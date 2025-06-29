package com.himalayas.authserver.controller;

import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.Tenant;
import com.himalayas.authserver.repo.RealmRepository;
import com.himalayas.authserver.repo.TenantRepository;
import com.himalayas.authserver.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/realms/{realmId}/tenants")
@RequiredArgsConstructor
public class TenantController {

  private final TenantRepository tenantRepository;
  private final RealmRepository realmRepository;
  private final SecurityUtil securityUtil;

  @GetMapping
  public List<Tenant> list(@PathVariable String realmId, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    Realm realm = realmRepository.findById(realmId)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
    return tenantRepository.findByRealm(realm);
  }

  @PostMapping
  public Tenant create(@PathVariable String realmId, @RequestBody Tenant tenant, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    Realm realm = realmRepository.findById(realmId)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
    tenant.setId(UUID.randomUUID().toString());
    tenant.setRealm(realm);
    return tenantRepository.save(tenant);
  }

  @GetMapping("/{id}")
  public Tenant get(@PathVariable String realmId, @PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    return tenantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
  }

  @PutMapping("/{id}")
  public Tenant update(@PathVariable String realmId, @PathVariable String id, @RequestBody Tenant tenantDto, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    Tenant tenant = tenantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));

    tenant.setName(tenantDto.getName());
    tenant.setDescription(tenantDto.getDescription());
    tenant.setEnabled(tenantDto.isEnabled());

    return tenantRepository.save(tenant);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String realmId, @PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    tenantRepository.deleteById(id);
  }
}
