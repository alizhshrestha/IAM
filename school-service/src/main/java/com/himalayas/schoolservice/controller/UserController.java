package com.himalayas.schoolservice.controller;

import com.himalayas.schoolservice.dto.response.SchoolResponseDto;
import com.himalayas.schoolservice.mapper.SchoolMapper;
import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final SchoolMapper schoolMapper;

  @GetMapping("/me/schools")
  public ResponseEntity<List<SchoolResponseDto>> getUserSchools(@CurrentUser AuthenticatedUser user) {
    String appUserId = user.getUserId();
    String tenantId = user.getTenantId();

    List<SchoolResponseDto> userSchools = schoolMapper.findUserSchools(tenantId, appUserId);
    if (userSchools.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userSchools);
  }
}
