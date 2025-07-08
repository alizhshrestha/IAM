package com.himalayas.schoolservice.controller;

import com.himalayas.schoolservice.dto.response.UserDto;
import com.himalayas.schoolservice.service.UserService;
import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.enums.RoleEnum;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDto>> getUsersBySchool(@CurrentUser AuthenticatedUser user, @RequestParam String schoolId) {
    if (!user.getRoles().contains(RoleEnum.ROLE_ADMIN.name())) {
      ResponseEntity.status(HttpStatus.FORBIDDEN.value()).build();
    }
    List<UserDto> users = userService.getUsersBySchool(schoolId);
    return ResponseEntity.ok(users);
  }
}
