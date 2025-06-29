package com.himalayas.resourceserver.controller;

import com.himalayas.resourceserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

  private final SecurityUtils securityUtils;

  @GetMapping
  public ResponseEntity<?> listStudents(
          @AuthenticationPrincipal Jwt jwt,
          @RequestParam String tenantId) {
    if (!securityUtils.isInTenant(jwt, tenantId)) {
      throw new AccessDeniedException("Access denied for this tenant.");
    }

    return ResponseEntity.ok(List.of(
            Map.of("name", "John", "tenant", tenantId),
            Map.of("name", "Jane", "tenant", tenantId)
    ));
  }
}
