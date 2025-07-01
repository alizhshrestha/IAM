package com.himalayas.authserver.config;

import com.himalayas.authserver.filter.TenantFilter;
import com.himalayas.authserver.service.TenantAwareUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.TE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {
  private final TenantAwareUserDetailsService tenantAwareUserDetailsService;
  private final TenantFilter tenantFilter;
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
            .authorizeHttpRequests(authorize ->
                    authorize
                            .anyRequest().authenticated()
            )
            .addFilterBefore(tenantFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return tenantAwareUserDetailsService;
  }
}
