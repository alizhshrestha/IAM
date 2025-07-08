package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, String> {
  Optional<Tenant> findByDomain(String domain);
  Optional<Tenant> findByDomainIgnoreCase(String domain);
}
