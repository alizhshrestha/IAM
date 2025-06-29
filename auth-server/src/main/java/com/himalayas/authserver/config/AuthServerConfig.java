package com.himalayas.authserver.config;

import com.himalayas.authserver.context.RealmContext;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.Role;
import com.himalayas.authserver.model.User;
import com.himalayas.authserver.repo.RealmRepository;
import com.himalayas.authserver.repo.UserRepository;
import com.himalayas.authserver.service.RealmAwareUserDetailsService;
import com.himalayas.authserver.util.KeyGeneratorUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class AuthServerConfig {

  @Bean
  public SecurityFilterChain authorizeSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
            OAuth2AuthorizationServerConfigurer.authorizationServer();
    http
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, (authorizationServer) ->
                    authorizationServer
                            .oidc(Customizer.withDefaults())    // Initialize `OidcConfigurer`
            )
            .formLogin(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
    http
            .securityMatcher("/realms/{realm}/oauth2/**")
            .authorizeHttpRequests(authz -> authz
                    .anyRequest().authenticated())
            .csrf(csrf-> csrf.ignoringRequestMatchers("/realms/{realm}/oauth2/**"))
//            .formLogin(Customizer.withDefaults())
    ;
    return http.build();
  }

  /*@Bean
  public RegisteredClientRepository inMemoryRegisteredClientRepository(){
    RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("school-mgmt-client")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("https://localhost:3000/callback")
            .scope(OidcScopes.OPENID)
            .scope("read")
            .scope("write")
            .clientSettings(ClientSettings.builder()
                    .requireProofKey(true)
                    .build())
            .build();

    return new InMemoryRegisteredClientRepository(registeredClient);
  }*/

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  private static RSAKey generateRsa() {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
    return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .privateKey((RSAPrivateKey) keyPair.getPrivate())
            .keyID(UUID.randomUUID().toString())
            .build();
  }

  @Bean
  public AuthorizationServerSettings providerSettings() {
    return AuthorizationServerSettings.builder()
            .issuer("https://localhost:9000")
            .build();
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, RealmAwareUserDetailsService userDetailsService) throws Exception{
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UserRepository userRepository, RealmRepository realmRepository) {
    return context -> {
      if(context.getPrincipal() instanceof UsernamePasswordAuthenticationToken authentication){
        String username = authentication.getName();
        String currentRealm = RealmContext.getRealm();
        if(currentRealm == null) return;
        Realm realm = realmRepository.findByName(currentRealm).orElseThrow(() -> new RuntimeException("Realm not found in db: " + currentRealm));
        Optional<User> userOpt = userRepository.findByUsernameAndRealm(username, realm);
        if(userOpt.isEmpty()) return;

        User user = userOpt.get();
        context.getClaims().claim("realm", currentRealm);
        context.getClaims().claim("tenant_id", user.getTenant().getId());

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        context.getClaims().claim("roles", roles);
        context.getClaims().issuer("https://localhost:9000/realms/" + currentRealm);
      }
    };
  }
}
