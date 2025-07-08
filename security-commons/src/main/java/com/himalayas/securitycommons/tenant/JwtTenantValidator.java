package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.entities.Tenant;
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

    Tenant tenant = tenantService.findByIssuer(issuer)
            .orElseThrow(() -> new IllegalArgumentException("Invalid issuer: " + issuer));
    TenantContextHolder.setTenant(tenant);
    return tenant;
  }

}
