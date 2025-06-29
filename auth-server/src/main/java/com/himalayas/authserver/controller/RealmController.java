package com.himalayas.authserver.controller;

import com.himalayas.authserver.dto.RealmDto;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.repo.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/realms")
@RequiredArgsConstructor
public class RealmController {

  private final RealmRepository realmRepository;

  @GetMapping
  public List<RealmDto> getAll(@AuthenticationPrincipal Jwt jwt) {
    checkAccess(jwt);
    return realmRepository.findAll().stream()
            .map(this::toDto)
            .toList();
  }

  @GetMapping("/{id}")
  public RealmDto getById(String id, @AuthenticationPrincipal Jwt jwt) {
    checkAccess(jwt);
    Realm realm = realmRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
    return toDto(realm);
  }

  @PostMapping
  public RealmDto create(@RequestBody RealmDto dto, @AuthenticationPrincipal Jwt jwt) {
    checkAccess(jwt);
    Realm realm = new Realm();
    realm.setId(UUID.randomUUID().toString());
    realm.setName(dto.getName());
    realm.setDescription(dto.getDescription());
    realm.setEnabled(dto.isEnabled());

    Realm saved = realmRepository.save(realm);
    return toDto(saved);
  }

  @PutMapping("/{id}")
  public RealmDto update(@RequestBody RealmDto dto, @PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    checkAccess(jwt);
    Realm realm = realmRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
    realm.setName(dto.getName());
    realm.setDescription(dto.getDescription());
    realm.setEnabled(dto.isEnabled());

    Realm saved = realmRepository.save(realm);
    return toDto(saved);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    checkAccess(jwt);
    realmRepository.deleteById(id);
  }

  private RealmDto toDto(Realm realm) {
    return RealmDto.builder()
            .id(realm.getId())
            .name(realm.getName())
            .description(realm.getDescription())
            .enabled(realm.isEnabled())
            .build();
  }

  private void checkAccess(Jwt jwt) {
    if (jwt.getClaimAsStringList("roles").contains("SUPER_ADMIN")) return;
    throw new RuntimeException("Access denied");
  }

}
