package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "registered_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClient extends AuditableEntity {
  @Id
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