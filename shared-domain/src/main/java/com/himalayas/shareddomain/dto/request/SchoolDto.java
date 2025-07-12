package com.himalayas.shareddomain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {
  private String schoolId;
  private String schoolName;
  private String tenantId;
  private String tenantDomain;
  private String clientId;
  private String clientName;
  private String redirectUri;
  private String scopes;
}
