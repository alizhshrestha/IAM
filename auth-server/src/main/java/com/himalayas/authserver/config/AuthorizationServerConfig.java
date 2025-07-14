package com.himalayas.authserver.config;

import com.himalayas.authserver.filter.TenantFilter;
import com.himalayas.authserver.mapper.TenantAwareRegisteredClientMapper;
import com.himalayas.authserver.repository.TenantAwareClientRegistrationRepository;
import com.himalayas.authserver.repository.TenantAwareRegisteredClientRepository;
import com.himalayas.authserver.service.TenantAwareUserDetailsService;
import com.himalayas.securitycommons.tenant.TenantContextHolder;
import com.himalayas.securitycommons.user.UserContextHolder;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {
  private final TenantAwareUserDetailsService tenantAwareUserDetailsService;
  private final TenantAwareClientRegistrationRepository clientRegistrationRepository;
  private final TenantFilter tenantFilter;
  private final String[] allowedOrigin = {
          "/oauth2/token",
          "/.well-known/**",
          "/oauth2/jwks",
          "/login",
          "/default-ui.css",
          "/favicon.ico",
          "/public/**",
          "/logout",
          "/connect/logout"
  };

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
          throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
            OAuth2AuthorizationServerConfigurer.authorizationServer();

    http
            .cors(Customizer.withDefaults())
            .addFilterBefore(tenantFilter, SecurityContextHolderFilter.class)
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, (authorizationServer) ->
                    authorizationServer
                            .oidc(Customizer.withDefaults())    // Enable OpenID Connect 1.0
            )
            .authorizeHttpRequests((authorize) ->
                    authorize
                            .requestMatchers(allowedOrigin).permitAll()
                            .anyRequest().authenticated()
            )
            // Redirect to the login page when not authenticated from the
            // authorization endpoint
            .exceptionHandling((exceptions) -> exceptions
                    .defaultAuthenticationEntryPointFor(
                            new LoginUrlAuthenticationEntryPoint("/login"),
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            );

    return http.build();
  }

  /**
   * Configures the default Spring Security filter chain for the application.
   * This handles general web security, including form login for users.
   *
   * @param http The HttpSecurity object to configure.
   * @return A SecurityFilterChain instance.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(Customizer.withDefaults())
            .addFilterBefore(tenantFilter, SecurityContextHolderFilter.class) // <== important!
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(allowedOrigin).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form.successHandler(
                    new SavedRequestAwareAuthenticationSuccessHandler()
            ))
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)));

    return http.build();
  }


  @Bean
  public LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
    OidcClientInitiatedLogoutSuccessHandler handler =
            new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    handler.setPostLogoutRedirectUri("{baseUrl}/logout");
    return (request, response, authentication) -> {
      try {
        handler.onLogoutSuccess(request, response, authentication);
      } finally {
        // Ensure cleanup even on logout endpoint
        UserContextHolder.clear();
        TenantContextHolder.clear();
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(tenantAwareUserDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    return tenantAwareUserDetailsService;
//  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(
          TenantAwareRegisteredClientMapper mapper) {
    System.out.println("✅ Using custom TenantAwareRegisteredClientRepository");
    return new TenantAwareRegisteredClientRepository(mapper);
  }
}
