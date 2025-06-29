package com.himalayas.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "oauth2_authorization_consent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuth2AuthorizationConsent {

  @EmbeddedId
  private AuthorizationConsentId id;

  @Column(length = 1000)
  private String authorities;
}

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AuthorizationConsentId implements Serializable {

  @Column(name = "registered_client_id", length = 100)
  private String registeredClientId;

  @Column(name = "principal_name", length = 200)
  private String principalName;
}
