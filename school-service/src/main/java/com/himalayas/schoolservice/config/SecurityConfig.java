package com.himalayas.schoolservice.config;

import com.himalayas.securitycommons.config.CustomJwtAuthenticationConverter;
import com.himalayas.securitycommons.tenant.JwtTenantValidator;
import com.himalayas.shareddomain.entities.Tenant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

import java.text.ParseException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final JwtTenantValidator jwtTenantValidator;

  public SecurityConfig(JwtTenantValidator jwtTenantValidator) {
    this.jwtTenantValidator = jwtTenantValidator;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, CustomJwtAuthenticationConverter customJwtAuthenticationConverter) throws Exception {
    http
            .cors(Customizer.withDefaults())
//            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/**", "/public/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                            .decoder(jwtDecoder(jwtTenantValidator))
                            .jwtAuthenticationConverter(customJwtAuthenticationConverter)
                    )
            )
//            .addFilterAfter(userContextCleanupFilter, SecurityContextHolderFilter.class)
    ;

    return http.build();
  }

/*  @Bean
  public JwtDecoder jwtDecoder(JwtTenantValidator jwtTenantValidator) {
    return new TenantAwareJwtDecoder(jwtTenantValidator);
  }*/

  @Bean
  public JwtDecoder jwtDecoder(JwtTenantValidator tenantValidator) {
    return token -> {
      try {
        Tenant tenant = tenantValidator.resolveTenant(token);
        String issuerUri = "http://" + tenant.getDomain() + ":9000";
        return JwtDecoders.fromIssuerLocation(issuerUri).decode(token);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
