package com.himalayas.authserver.controller;

import com.himalayas.authserver.config.PermissionEvaluator;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.Role;
import com.himalayas.authserver.repo.RealmRepository;
import com.himalayas.authserver.repo.RoleRepository;
import com.himalayas.authserver.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/realms/{realmId}/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleRepository roleRepository;
  private final RealmRepository realmRepository;
  private final SecurityUtil securityUtil;

  @GetMapping
  public List<Role> list(@PathVariable String realmId, @AuthenticationPrincipal Jwt jwt){
    securityUtil.checkAccess(jwt, realmId);
    return roleRepository.findByRealm(realmRepository.findById(realmId).orElseThrow(() -> new RuntimeException("Realm not found")));
  }

  @PostMapping
  public Role create(@PathVariable String realmId, @RequestBody Role role, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    Realm realm = realmRepository.findById(realmId).orElseThrow(() -> new RuntimeException("Realm not found"));
    role.setId(UUID.randomUUID().toString());
    role.setRealm(realm);
    return roleRepository.save(role);
  }

  @DeleteMapping("/{roleId}")
  public void delete(@PathVariable String realmId, @PathVariable String roleId, @AuthenticationPrincipal Jwt jwt) {
    securityUtil.checkAccess(jwt, realmId);
    roleRepository.deleteById(roleId);
  }

}
