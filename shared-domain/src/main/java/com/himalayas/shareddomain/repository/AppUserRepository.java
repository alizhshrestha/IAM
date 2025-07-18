package com.himalayas.shareddomain.repository;

import com.himalayas.shareddomain.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByUsernameAndTenantId(String username, String tenantId);
}
