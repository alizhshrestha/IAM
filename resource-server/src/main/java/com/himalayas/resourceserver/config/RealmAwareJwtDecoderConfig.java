package com.himalayas.resourceserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

@Configuration
public class RealmAwareJwtDecoderConfig {

  @Value("${auth-server.base-url}")
  private String authServerBaseUrl;

  private final Cache<String, NimbusJwtDecoder> decoderCache =
          Caffeine.newBuilder()
                  .expireAfterAccess(Duration.ofHours(1))
                  .build();

  @Bean
  public JwtDecoder jwtDecoder() {
    return token -> {
      String realm = getRealmFromToken(token);
      NimbusJwtDecoder decoder = decoderCache.asMap().computeIfAbsent(realm, this::buildDecoderForRealm);
      return decoder.decode(token);
    };
  }

  private String getRealmFromToken(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length < 2) throw new IllegalArgumentException("Invalid JWT");
      String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

      JsonNode jsonNode = new ObjectMapper().readTree(payload);
      JsonNode realmNode = jsonNode.get("realm");

      if (realmNode == null || realmNode.asText().isBlank()) {
        throw new IllegalArgumentException("Realm claim missing in token.");
      }

      return realmNode.asText();

    } catch (Exception e) {
      throw new BadCredentialsException("Failed to parse JWT for realm ", e);
    }
  }

  private NimbusJwtDecoder buildDecoderForRealm(String realm) {
    String jwksUri = String.format("%s/realms/%s/.well-known/jwks.json", authServerBaseUrl, realm);
    return NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
  }
}
