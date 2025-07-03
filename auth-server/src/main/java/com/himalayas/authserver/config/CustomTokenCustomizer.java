package com.himalayas.authserver.config;

import com.himalayas.authserver.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    if (context.getTokenType().getValue().equals("access_token")) {
      Authentication principal = context.getPrincipal();
      CustomUserDetails user = (CustomUserDetails) principal.getPrincipal();
      context.getClaims().claim("tenant_id", user.getTenantId());
      context.getClaims().claim("email", user.getUsername());
      context.getClaims().claim("roles", user.getAppUser().getRoles());
    }
  }
}
