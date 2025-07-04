package com.himalayas.securitycommons.tenant;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class JwtTenantValidator {

  private final TenantService tenantService;

  public Tenant resolveTenant(String token) throws ParseException{
    SignedJWT jwt = SignedJWT.parse(token);
    JWTClaimsSet claims = jwt.getJWTClaimsSet();
    String issuer = claims.getIssuer();

    return tenantService.findByIssuer(issuer)
            .orElseThrow(() -> new IllegalArgumentException("Invalid issuer: " + issuer));
  }

}
