package com.himalayas.authserver.config;

import com.himalayas.authserver.service.CustomUserDetails;
import com.himalayas.shareddomain.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class JwtTokenCustomizerConfig {

  private final SchoolMapper schoolMapper;

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
    return context -> {
      if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
        Authentication principal = context.getPrincipal();
        if (principal.getPrincipal() instanceof CustomUserDetails userDetails) {
          Set<String> roles = userDetails.getAuthorities().stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.toSet());
          context.getClaims().claim("roles", roles);
          context.getClaims().claim("tenant", userDetails.getTenantId());
          context.getClaims().claim("app_user_id", userDetails.getAppUser().getId());
          context.getClaims().claim("email", userDetails.getUsername());
        }
      }
    };
  }
}
