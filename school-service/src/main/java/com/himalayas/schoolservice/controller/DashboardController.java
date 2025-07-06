package com.himalayas.schoolservice.controller;

import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {

/*  @GetMapping("/me")
  public ResponseEntity<?> me(@AuthenticationPrincipal Jwt jwt){
    Map<String, Object> user = Map.of(
            "username", jwt.getSubject(),
            "tenant", jwt.getIssuer(),
            "roles", jwt.getClaimAsStringList("authorities")
    );
    return ResponseEntity.ok(user);
  }*/

  @GetMapping("/me")
  public ResponseEntity<?> getMyInfo(@CurrentUser AuthenticatedUser user){
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found in context");
    }

    return ResponseEntity.ok(user);
  }
}

