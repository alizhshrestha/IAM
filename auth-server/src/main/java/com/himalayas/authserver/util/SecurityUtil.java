package com.himalayas.authserver.util;

import com.himalayas.authserver.config.PermissionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityUtil {

  private final PermissionEvaluator permissionEvaluator;

  public void checkAccess(Jwt jwt, String realmId) {
    if (permissionEvaluator.isSuperAdmin(jwt)) return;
    if (permissionEvaluator.isRealmAdmin(jwt) && permissionEvaluator.isInRealm(jwt, realmId)) return;
    throw new AccessDeniedException("Access denied");
  }
}
