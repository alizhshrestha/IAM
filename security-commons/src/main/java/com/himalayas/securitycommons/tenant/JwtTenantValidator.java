package com.himalayas.securitycommons.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.himalayas.shareddomain.dto.response.TenantResponseDto;
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
  private final ObjectMapper objectMapper;

  public Tenant resolveTenant(String token) throws ParseException {
    SignedJWT jwt = SignedJWT.parse(token);
    JWTClaimsSet claims = jwt.getJWTClaimsSet();
    String issuer = claims.getIssuer();

    Object tenantResponse = tenantService.findByIssuer(issuer)
            .orElseThrow(() -> new IllegalArgumentException("Invalid issuer: " + issuer));
    TenantResponseDto tenantResponseDto = objectMapper.convertValue(tenantResponse, TenantResponseDto.class);
    Tenant tenant = new Tenant();
    tenant.setId(tenantResponseDto.getId());
    tenant.setName(tenantResponseDto.getName());
    tenant.setDomain(tenantResponseDto.getDomain());
    TenantContextHolder.setTenant(tenant);
    return tenant;
  }

}
