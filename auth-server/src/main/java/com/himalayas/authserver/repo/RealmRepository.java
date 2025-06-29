package com.himalayas.authserver.repo;

import com.himalayas.authserver.model.Realm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealmRepository extends JpaRepository<Realm, String> {
  Optional<Realm> findByName(String name);
}
