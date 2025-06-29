package com.himalayas.authserver.repo;

import com.himalayas.authserver.model.OAuth2RegisteredClient;
import com.himalayas.authserver.model.Realm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OAuth2RegisteredClientRepository extends JpaRepository<OAuth2RegisteredClient, String> {
  Optional<OAuth2RegisteredClient> findByClientIdAndRealm(String clientId, Realm realm);

  List<OAuth2RegisteredClient> findByRealm(Realm realm);
}
