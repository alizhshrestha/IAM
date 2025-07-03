package com.himalayas.schoolservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtil {

  public static String getTenantId(Authentication authentication){
    if(authentication instanceof JwtAuthenticationToken token){
      Jwt jwt = token.getToken();
      return jwt.getClaimAsString("tenant_id");
    }
    return null;
  }

  public static String getUserEmail(Authentication authentication){
    if(authentication instanceof JwtAuthenticationToken token){
      Jwt jwt = token.getToken();
      return jwt.getClaimAsString("email");
    }
    return null;
  }

  public static boolean hasScope(Authentication authentication, String scope){
    if(authentication instanceof JwtAuthenticationToken token){
      Jwt jwt = token.getToken();
      return jwt.getClaimAsStringList("scope").contains(scope);
    }
    return false;
  }
}
