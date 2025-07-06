package com.himalayas.securitycommons.tenant;

import java.util.List;
import java.util.Optional;

public interface TenantService {
    Optional<Tenant> findByDomain(String domain);
    Optional<Tenant> findByIssuer(String issuer);
    Optional<List<Tenant>> findAll();
}
