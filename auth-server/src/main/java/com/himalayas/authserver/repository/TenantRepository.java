package com.himalayas.authserver.repository;

import com.himalayas.authserver.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

  /**
   * Finds a Tenant by its domain name. This is crucial for resolving the tenant
   * based on the incoming request's host header (subdomain).
   * @param domain The domain string (e.g., "tenant1.auth.example.com")
   * @return An Optional containing the Tenant if found, or empty otherwise.
   */
  Optional<Tenant> findByDomain(String domain);


}
