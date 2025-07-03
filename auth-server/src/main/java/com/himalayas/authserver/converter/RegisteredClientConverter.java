package com.himalayas.authserver.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.himalayas.authserver.dto.RegisteredClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegisteredClientConverter {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public static RegisteredClientDto from(RegisteredClient client, String tenantId) throws JsonProcessingException {
    RegisteredClientDto e = new RegisteredClientDto();
    e.setId(client.getId());
    e.setClientId(client.getClientId());
    e.setClientIdIssuedAt(client.getClientIdIssuedAt());
    e.setClientSecret(client.getClientSecret());
    e.setClientSecretExpiresAt(client.getClientSecretExpiresAt());
    e.setClientName(client.getClientName());
    e.setClientAuthenticationMethods(client.getClientAuthenticationMethods().stream()
            .map(ClientAuthenticationMethod::getValue).collect(Collectors.joining(",")));
    e.setAuthorizationGrantTypes(client.getAuthorizationGrantTypes().stream()
            .map(AuthorizationGrantType::getValue).collect(Collectors.joining(",")));
    e.setRedirectUris(String.join(",", client.getRedirectUris()));
    e.setScopes(String.join(",", client.getScopes()));
    e.setClientSettings(objectMapper.writeValueAsString(client.getClientSettings()));
    e.setTokenSettings(objectMapper.writeValueAsString(client.getTokenSettings()));
    e.setTenantId(tenantId);
    return e;
  }

  public static RegisteredClient toRegisteredClient(RegisteredClientDto dto) throws JsonProcessingException {
    Map<String, Object> rawClientMap = objectMapper.readValue(dto.getClientSettings(), new TypeReference<>() {});
    Map<String, Object> flattenedClientMap = flattenMap(rawClientMap);

    Map<String, Object> rawTokenMap = objectMapper.readValue(dto.getTokenSettings(), new TypeReference<>() {});
    Map<String, Object> flattenedTokenMap = flattenMap(rawTokenMap);

    return RegisteredClient.withId(dto.getId())
            .clientId(dto.getClientId())
            .clientIdIssuedAt(dto.getClientIdIssuedAt())
            .clientSecret(dto.getClientSecret())
            .clientSecretExpiresAt(dto.getClientSecretExpiresAt())
            .clientName(dto.getClientName())
            .clientAuthenticationMethods(auths ->
                    Arrays.stream(dto.getClientAuthenticationMethods().split(","))
                            .map(ClientAuthenticationMethod::new)
                            .forEach(auths::add))
            .authorizationGrantTypes(grants ->
                    Arrays.stream(dto.getAuthorizationGrantTypes().split(","))
                            .map(AuthorizationGrantType::new)
                            .forEach(grants::add))
            .redirectUris(uris -> Arrays.stream(dto.getRedirectUris().split(",")).forEach(uris::add))
            .scopes(s -> Arrays.stream(dto.getScopes().split(",")).forEach(s::add))
            .clientSettings(ClientSettings.withSettings(flattenedClientMap).build())
            .tokenSettings(TokenSettings.withSettings(flattenedTokenMap)
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.ofMinutes(30))
                    .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                    .build())
            .build();
  }

  private static Map<String, Object> flattenMap(Map<String, Object> map) {
    Map<String, Object> flat = new HashMap<>();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      if ("settings".equals(entry.getKey()) && entry.getValue() instanceof Map) {
        Map<String, Object> settingsMap = (Map<String, Object>) entry.getValue();
        for (Map.Entry<String, Object> e : settingsMap.entrySet()) {
          flat.put(e.getKey(), convertIfDurationKey(e.getKey(), e.getValue()));
        }
      } else {
        flat.put(entry.getKey(), convertIfDurationKey(entry.getKey(), entry.getValue()));
      }
    }
    return flat;
  }

  private static Object convertIfDurationKey(String key, Object value) {
    List<String> durationKeys = List.of(
            "settings.token.access-token-time-to-live",
            "settings.token.refresh-token-time-to-live",
            "settings.token.authorization-code-time-to-live",
            "settings.token.device-code-time-to-live"
    );

    if (durationKeys.contains(key) && value instanceof Number) {
      return Duration.ofSeconds(((Number) value).longValue());
    }

    return value;
  }




}
