package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@MappedTypes(SchoolClientsDto.ClientDto.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClientsDto {
  private String schoolId;
  private String schoolName;
  private String tenantId;
  private String tenantDomain;
  private List<ClientDto> clients;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ClientDto {
    private String clientId;
    private String clientName;
    private String redirectUri;
    private String scopes;
  }
}
