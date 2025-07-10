package com.himalayas.authserver.controller;

import com.himalayas.securitycommons.tenant.TenantService;
import com.himalayas.shareddomain.entities.Tenant;
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
  public ResponseEntity<List<Tenant>> getAllTenants(){
    Optional<List<Tenant>> tenantsOpt = tenantService.findAll();
    if(tenantsOpt.isPresent()){
      List<Tenant> tenants = tenantsOpt.get();
      List<Tenant> filteredTenants = tenants.stream().filter(tenant -> !tenant.getId().equals("public")).toList();
      return ResponseEntity.ok(filteredTenants);
    }
    return ResponseEntity.notFound().build();
  }
}
