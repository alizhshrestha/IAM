package com.himalayas.authserver.repository;

import com.himalayas.authserver.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByUsernameAndTenantId(String username, String tenantId);
}
