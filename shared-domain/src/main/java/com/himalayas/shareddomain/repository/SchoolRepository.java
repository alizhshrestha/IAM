package com.himalayas.shareddomain.repository;

import com.himalayas.shareddomain.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, String> {
  Optional<School> findByTenantId(String tenantId);
}
