package com.himalayas.resourceserver.util;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUtils {

  public boolean hasRole(Jwt jwt, String role){
    List<String> roles = jwt.getClaimAsStringList("roles");
    return roles != null && roles.contains(role);
  }

  public boolean isInTenant(Jwt jwt, String tenantId){
    return tenantId.equals(jwt.getClaimAsString("tenant_id"));
  }

  public boolean isInRealm(Jwt jwt, String realm){
    return realm.equals(jwt.getClaimAsString("realm"));
  }

}
