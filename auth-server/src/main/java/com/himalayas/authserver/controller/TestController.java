package com.himalayas.authserver.controller;

import com.himalayas.authserver.context.TenantContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/test-tenant")
  public String testTenant(){
    String tenantId = TenantContext.getCurrentTenant();
    return "Current Tenant Id: " + (tenantId != null ? tenantId : "NOT FOUND");
  }
}
