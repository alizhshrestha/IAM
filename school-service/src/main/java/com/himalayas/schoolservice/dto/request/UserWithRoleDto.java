package com.himalayas.schoolservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRoleDto {
  private String id;
  private String fullName;
  private String email;
  private String role;
}
