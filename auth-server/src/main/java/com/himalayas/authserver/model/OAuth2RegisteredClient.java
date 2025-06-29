package com.himalayas.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "oauth2_registered_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuth2RegisteredClient {

  @Id
  @Column(length = 100)
  private String id;

  @Column(name = "client_id", nullable = false, unique = true, length = 100)
  private String clientId;

  @Column(name = "client_id_issued_at")
  private Instant clientIdIssuedAt;

  @Column(name = "client_secret", length = 200)
  private String clientSecret;

  @Column(name = "client_secret_expires_at")
  private Instant clientSecretExpiresAt;

  @Column(name = "client_name", length = 200)
  private String clientName;

  @Column(name = "client_authentication_methods", length = 1000)
  private String clientAuthenticationMethods;

  @Column(name = "authorization_grant_types", length = 1000)
  private String authorizationGrantTypes;

  @Column(name = "redirect_uris", length = 1000)
  private String redirectUris;

  @Column(name = "scopes", length = 1000)
  private String scopes;

  @Column(name = "client_settings", length = 2000)
  private String clientSettings;

  @Column(name = "token_settings", length = 2000)
  private String tokenSettings;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "realm_id")
  private Realm realm;
}
