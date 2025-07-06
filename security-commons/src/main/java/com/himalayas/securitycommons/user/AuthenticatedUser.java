package com.himalayas.securitycommons.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticatedUser {
  private String username;
  private List<String> roles;
  private String tenantId;
  private String userId;
  private String email;

  public boolean hasRole(String role) {
    return roles != null && roles.contains(role);
  }
}
