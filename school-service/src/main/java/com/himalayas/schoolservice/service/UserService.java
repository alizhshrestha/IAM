package com.himalayas.schoolservice.service;

import com.himalayas.schoolservice.dto.request.UserWithRoleDto;
import com.himalayas.schoolservice.dto.response.UserDto;
import com.himalayas.schoolservice.mapper.UserMapper;
import com.himalayas.securitycommons.tenant.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;

  public List<UserDto> getUsersBySchool(String schoolId) {
    String tenantId = TenantContextHolder.getTenantId();
    List<UserWithRoleDto> flatList = userMapper.findUsersBySchool(tenantId, schoolId);

    Map<String, UserDto> grouped = new LinkedHashMap<>();
    for (UserWithRoleDto row : flatList) {
      grouped.computeIfAbsent(row.getId(), id -> new UserDto(
              row.getId(),
              row.getFullName(),
              row.getEmail(),
              new ArrayList<>()
      )).getRoles().add(row.getRole());
    }

    return new ArrayList<>(grouped.values());
  }
}
