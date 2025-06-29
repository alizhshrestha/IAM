package com.himalayas.resourceserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

  @GetMapping
  public Map<String, Object> profile(@AuthenticationPrincipal Jwt jwt){
    return Map.of(
            "user", jwt.getSubject(),
            "realm", jwt.getClaimAsString("realm"),
            "tenant", jwt.getClaimAsString("tenant_id"),
            "roles", jwt.getClaimAsStringList("roles")
    );
  }
}
