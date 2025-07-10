package com.himalayas.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
  private String clientId;
  private String redirectUris;
  private String tenantId;
  private String scopes;
  private String clientName;
}
