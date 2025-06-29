package com.himalayas.authserver.controller;

import com.himalayas.authserver.config.PermissionEvaluator;
import com.himalayas.authserver.dto.UserDto;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.Role;
import com.himalayas.authserver.model.Tenant;
import com.himalayas.authserver.model.User;
import com.himalayas.authserver.repo.RealmRepository;
import com.himalayas.authserver.repo.RoleRepository;
import com.himalayas.authserver.repo.TenantRepository;
import com.himalayas.authserver.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/realms/{realmId}/tenants/{tenantId}/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;
  private final RealmRepository realmRepository;
  private final TenantRepository tenantRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final PermissionEvaluator permissionEvaluator;

  @GetMapping
  public List<UserDto> list(
          @PathVariable String realmId,
          @PathVariable String tenantId,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId, tenantId);

    return userRepository.findByRealmIdAndTenantId(realmId, tenantId)
            .stream()
            .map(this::toDto)
            .toList();
  }

  @PostMapping
  public UserDto create(
          @PathVariable String realmId,
          @PathVariable String tenantId,
          @RequestBody UserDto dto,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId, tenantId);

    Realm realm = getRealm(realmId);
    Tenant tenant = getTenant(tenantId);

    User user = User.builder()
            .id(UUID.randomUUID().toString())
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .enabled(dto.isEnabled())
            .realm(realm)
            .tenant(tenant)
            .roles(getRoles(dto.getRoleIds(), realmId))
            .build();

    return toDto(userRepository.save(user));
  }

  @PutMapping("/{userId}")
  public UserDto update(
          @PathVariable String realmId,
          @PathVariable String tenantId,
          @PathVariable String userId,
          @RequestBody UserDto dto,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId, tenantId);

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    validateOwnership(user, realmId, tenantId);

    user.setUsername(dto.getUsername());
    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
      user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }
    user.setEnabled(dto.isEnabled());
    user.setRoles(getRoles(dto.getRoleIds(), realmId));

    return toDto(userRepository.save(user));
  }

  @DeleteMapping("/{userId}")
  public void delete(
          @PathVariable String realmId,
          @PathVariable String tenantId,
          @PathVariable String userId,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId, tenantId);

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    validateOwnership(user, realmId, tenantId);

    userRepository.delete(user);
  }

  // ------------ Helpers ------------

  private Set<Role> getRoles(Set<String> roleIds, String realmId) {
    return roleRepository.findAllById(roleIds).stream()
            .filter(role -> role.getRealm().getId().equals(realmId))
            .collect(Collectors.toSet());
  }

  private UserDto toDto(User user) {
    return UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .enabled(user.isEnabled())
            .tenantId(user.getTenant().getId())
            .roleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()))
            .build();
  }

  private Realm getRealm(String realmId) {
    return realmRepository.findById(realmId)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
  }

  private Tenant getTenant(String tenantId) {
    return tenantRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
  }

  private void checkAccess(Jwt jwt, String realmId, String tenantId) {
    if (permissionEvaluator.isSuperAdmin(jwt)) return;
    if (permissionEvaluator.isRealmAdmin(jwt) && permissionEvaluator.isInRealm(jwt, realmId)) return;
    throw new AccessDeniedException("Access denied.");
  }

  private void validateOwnership(User user, String realmId, String tenantId) {
    if (!user.getRealm().getId().equals(realmId) ||
            !user.getTenant().getId().equals(tenantId)) {
      throw new AccessDeniedException("User does not belong to specified realm/tenant.");
    }
  }
}
