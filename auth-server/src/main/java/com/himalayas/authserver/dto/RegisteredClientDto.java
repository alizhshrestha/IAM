package com.himalayas.authserver.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RegisteredClientDto {
  private String id;
  private String clientId;
  private Instant clientIdIssuedAt;
  private String clientSecret;
  private Instant clientSecretExpiresAt;
  private String clientName;
  private String clientAuthenticationMethods;
  private String authorizationGrantTypes;
  private String redirectUris;
  private String scopes;
  private String clientSettings;
  private String tokenSettings;
  private String tenantId;
}

