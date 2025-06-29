package com.himalayas.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "oauth2_authorization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuth2Authorization {

  @Id
  @Column(length = 100)
  private String id;

  @Column(name = "registered_client_id", nullable = false, length = 100)
  private String registeredClientId;

  @Column(name = "principal_name", nullable = false, length = 200)
  private String principalName;

  @Column(name = "authorization_grant_type", nullable = false, length = 100)
  private String authorizationGrantType;

  @Column(name = "attributes", columnDefinition = "BLOB")
  private String attributes;

  private String state;

  @Column(name = "authorization_code_value", columnDefinition = "BLOB")
  private String authorizationCodeValue;
  private Instant authorizationCodeIssuedAt;
  private Instant authorizationCodeExpiresAt;
  @Column(name = "authorization_code_metadata", columnDefinition = "BLOB")
  private String authorizationCodeMetadata;

  @Column(name = "access_token_value", columnDefinition = "BLOB")
  private String accessTokenValue;
  private Instant accessTokenIssuedAt;
  private Instant accessTokenExpiresAt;
  @Column(name = "access_token_metadata", columnDefinition = "BLOB")
  private byte[] accessTokenMetadata;
  private String accessTokenType;
  private String accessTokenScopes;

  @Column(name = "oidc_id_token_value", columnDefinition = "BLOB")
  private byte[] oidcIdTokenValue;
  private Instant oidcIdTokenIssuedAt;
  private Instant oidcIdTokenExpiresAt;
  @Column(name = "oidc_id_token_metadata", columnDefinition = "BLOB")
  private byte[] oidcIdTokenMetadata;

  @Column(name = "refresh_token_value", columnDefinition = "BLOB")
  private byte[] refreshTokenValue;
  private Instant refreshTokenIssuedAt;
  private Instant refreshTokenExpiresAt;
  @Column(name = "refresh_token_metadata", columnDefinition = "BLOB")
  private byte[] refreshTokenMetadata;
}
