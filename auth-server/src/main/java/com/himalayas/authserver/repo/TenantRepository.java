package com.himalayas.authserver.repo;

import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, String> {
  List<Tenant> findByRealm(Realm realm);
}
