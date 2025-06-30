package com.himalayas.authserver.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.himalayas.authserver.model.OAuth2RegisteredClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class OAuthClientMapper {

  public static RegisteredClient toRegisteredClient(OAuth2RegisteredClient entity) {
    return RegisteredClient.withId(entity.getId())
            .clientId(entity.getClientId())
            .clientIdIssuedAt(entity.getClientIdIssuedAt())
            .clientSecret(entity.getClientSecret())
            .clientSecretExpiresAt(entity.getClientSecretExpiresAt())
            .clientName(entity.getClientName())
            .clientAuthenticationMethods(authMethods -> {
              Arrays.stream(entity.getClientAuthenticationMethods().split(","))
                      .map(String::trim)
                      .map(ClientAuthenticationMethod::new)
                      .forEach(authMethods::add);
            })
            .authorizationGrantTypes(grantTypes -> {
              Arrays.stream(entity.getAuthorizationGrantTypes().split(","))
                      .map(String::trim)
                      .map(AuthorizationGrantType::new)
                      .forEach(grantTypes::add);
            })
            .redirectUris(uris -> {
              Arrays.stream(entity.getRedirectUris().split(","))
                      .map(String::trim)
                      .forEach(uris::add);
            })
            .scopes(scopes -> {
              Arrays.stream(entity.getScopes().split(","))
                      .map(String::trim)
                      .forEach(scopes::add);
            })
            .clientSettings(ClientSettings.withSettings(parseSetting(entity.getClientSettings())).build())
            .tokenSettings(TokenSettings.withSettings(parseSetting(entity.getTokenSettings())).build())
            .build();
  }

  private static Map<String, Object> parseSetting(String settings) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(settings, new TypeReference<>() {
      });
    } catch (Exception ex) {
      return Collections.emptyMap();
    }
  }
}
