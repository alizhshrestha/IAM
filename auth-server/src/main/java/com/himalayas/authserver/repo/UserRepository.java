package com.himalayas.authserver.repo;

import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameAndRealm(String username, Realm realm);

  List<User> findByRealmIdAndTenantId(String realmId, String tenantId);
}
