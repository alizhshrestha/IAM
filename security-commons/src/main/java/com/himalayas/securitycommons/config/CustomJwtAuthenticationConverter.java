package com.himalayas.securitycommons.config;

import com.himalayas.securitycommons.tenant.TenantContextHolder;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import com.himalayas.securitycommons.user.UserContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    String username = jwt.getSubject();
    List<String> roles = jwt.getClaimAsStringList("roles");
    String tenantId = extractTenantId();
    String appUserId = jwt.getClaimAsString("app_user_id");
    String email = jwt.getClaimAsString("email");

    AuthenticatedUser user = new AuthenticatedUser(username, roles, tenantId, appUserId, email);
    UserContextHolder.setUser(user);

    List<GrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new JwtAuthenticationToken(jwt, authorities, username);
  }

  private String extractTenantId() {
    return TenantContextHolder.getTenant().getId();
  }
}
