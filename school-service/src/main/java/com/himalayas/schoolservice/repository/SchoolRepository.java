package com.himalayas.schoolservice.repository;

import com.himalayas.shareddomain.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {
  Optional<School> findByTenantId(String tenantId);
}
