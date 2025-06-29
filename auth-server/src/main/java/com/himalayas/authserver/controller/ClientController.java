package com.himalayas.authserver.controller;

import com.himalayas.authserver.config.PermissionEvaluator;
import com.himalayas.authserver.model.OAuth2RegisteredClient;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.repo.OAuth2RegisteredClientRepository;
import com.himalayas.authserver.repo.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/realms/{realmId}/clients")
@RequiredArgsConstructor
public class ClientController {

  private final OAuth2RegisteredClientRepository clientRepository;
  private final RealmRepository realmRepository;
  private final PermissionEvaluator permissionEvaluator;

  /**
   * List all clients for a realm
   */
  @GetMapping
  public List<OAuth2RegisteredClient> list(
          @PathVariable String realmId,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId);
    Realm realm = getRealm(realmId);
    return clientRepository.findByRealm(realm);
  }

  /**
   * Create a new client in a realm
   */
  @PostMapping
  public OAuth2RegisteredClient create(
          @PathVariable String realmId,
          @RequestBody OAuth2RegisteredClient client,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId);
    Realm realm = getRealm(realmId);

    client.setId(UUID.randomUUID().toString());
    client.setRealm(realm);
    client.setClientIdIssuedAt(Instant.now());

    return clientRepository.save(client);
  }

  /**
   * Get a specific client by ID
   */
  @GetMapping("/{clientId}")
  public OAuth2RegisteredClient get(
          @PathVariable String realmId,
          @PathVariable String clientId,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId);

    OAuth2RegisteredClient client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    if (!client.getRealm().getId().equals(realmId)) {
      throw new AccessDeniedException("Client does not belong to this realm.");
    }

    return client;
  }

  /**
   * Update client
   */
  @PutMapping("/{clientId}")
  public OAuth2RegisteredClient update(
          @PathVariable String realmId,
          @PathVariable String clientId,
          @RequestBody OAuth2RegisteredClient updatedClient,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId);

    OAuth2RegisteredClient client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    if (!client.getRealm().getId().equals(realmId)) {
      throw new AccessDeniedException("Client does not belong to this realm.");
    }

    client.setClientName(updatedClient.getClientName());
    client.setClientSecret(updatedClient.getClientSecret());
    client.setClientSecretExpiresAt(updatedClient.getClientSecretExpiresAt());
    client.setRedirectUris(updatedClient.getRedirectUris());
    client.setAuthorizationGrantTypes(updatedClient.getAuthorizationGrantTypes());
    client.setClientAuthenticationMethods(updatedClient.getClientAuthenticationMethods());
    client.setScopes(updatedClient.getScopes());
    client.setClientSettings(updatedClient.getClientSettings());
    client.setTokenSettings(updatedClient.getTokenSettings());

    return clientRepository.save(client);
  }

  /**
   * Delete client
   */
  @DeleteMapping("/{clientId}")
  public void delete(
          @PathVariable String realmId,
          @PathVariable String clientId,
          @AuthenticationPrincipal Jwt jwt) {

    checkAccess(jwt, realmId);

    OAuth2RegisteredClient client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    if (!client.getRealm().getId().equals(realmId)) {
      throw new AccessDeniedException("Client does not belong to this realm.");
    }

    clientRepository.delete(client);
  }

  // ------------------- Helper Methods -----------------------

  private Realm getRealm(String realmId) {
    return realmRepository.findById(realmId)
            .orElseThrow(() -> new RuntimeException("Realm not found"));
  }

  private void checkAccess(Jwt jwt, String realmId) {
    if (permissionEvaluator.isSuperAdmin(jwt)) return;
    if (permissionEvaluator.isRealmAdmin(jwt) && permissionEvaluator.isInRealm(jwt, realmId)) return;
    throw new AccessDeniedException("Access denied.");
  }
}
