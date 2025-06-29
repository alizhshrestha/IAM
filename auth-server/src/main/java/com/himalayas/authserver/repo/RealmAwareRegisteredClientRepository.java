package com.himalayas.authserver.repo;

import com.himalayas.authserver.context.RealmContext;
import com.himalayas.authserver.mapper.OAuthClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RealmAwareRegisteredClientRepository implements RegisteredClientRepository {

  //  private final RegisteredClientRepository delegate;
  private final OAuth2RegisteredClientRepository clientRepository;
  private final RealmRepository realmRepository;

  @Override
  public void save(RegisteredClient registeredClient) {
    throw new UnsupportedOperationException("Saving not implemented yet.");
  }

  @Override
  public RegisteredClient findById(String id) {
    return clientRepository.findById(id)
            .map(OAuthClientMapper::toRegisteredClient)
            .orElse(null);
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    String currentRealm = RealmContext.getRealm();
    if (currentRealm == null) {
      return null;
    }

    return realmRepository.findByName(currentRealm)
            .flatMap(realm -> clientRepository.findByClientIdAndRealm(clientId, realm))
            .map(OAuthClientMapper::toRegisteredClient)
            .orElse(null);
  }
}
