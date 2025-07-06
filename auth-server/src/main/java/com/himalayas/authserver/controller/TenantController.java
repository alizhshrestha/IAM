package com.himalayas.authserver.controller;

import com.himalayas.securitycommons.tenant.Tenant;
import com.himalayas.securitycommons.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api/tenants")
@RequiredArgsConstructor
public class TenantController {

  private final TenantService tenantService;

  @GetMapping
  public ResponseEntity<List<String>> getAllTenants(){
    Optional<List<Tenant>> tenantsOpt = tenantService.findAll();
    return tenantsOpt.map(tenants -> ResponseEntity.ok(tenants.stream().map(Tenant::getId).toList())).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
