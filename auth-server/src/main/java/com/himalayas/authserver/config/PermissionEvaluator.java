package com.himalayas.authserver.config;

import com.himalayas.authserver.repo.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

  private final RealmRepository realmRepository;

  public boolean isSuperAdmin(Jwt jwt){
    return jwt.getClaimAsStringList("roles").contains("SUPER_ADMIN");
  }

  public boolean isRealmAdmin(Jwt jwt){
    return jwt.getClaimAsStringList("roles").contains("REALM_ADMIN");
  }

  public boolean isInRealm(Jwt jwt, String realmId){
    String currentRealm = jwt.getClaim("realm");
    return currentRealm != null && currentRealm.equalsIgnoreCase(realmId);
  }
}
