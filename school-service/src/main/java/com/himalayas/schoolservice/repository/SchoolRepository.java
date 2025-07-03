package com.himalayas.schoolservice.repository;

import com.himalayas.schoolservice.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, String> {
  List<School> findAllByTenantId(String tenantId);

  Optional<School> findByIdAndTenantId(String id, String tenantId);
}
