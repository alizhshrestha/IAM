package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {
  Optional<Tenant> findByDomain(String domain);
  Optional<Tenant> findByDomainIgnoreCase(String domain);
}
