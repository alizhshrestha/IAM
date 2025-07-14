package com.himalayas.authserver.repository;

import com.himalayas.authserver.dto.RegisteredClientDto;
import com.himalayas.authserver.mapper.TenantAwareRegisteredClientMapper;
import com.himalayas.securitycommons.tenant.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TenantAwareClientRegistrationRepository implements ClientRegistrationRepository {

  private final TenantAwareRegisteredClientMapper registeredClientMapper;

  @Override
  public ClientRegistration findByRegistrationId(String registrationId) {
    String tenantId = TenantContextHolder.getTenant().getId();
    RegisteredClientDto dto = registeredClientMapper.findByClientId(registrationId, tenantId);

    if (dto == null) {
      throw new IllegalArgumentException("Client not found for tenant: " + tenantId + ", client: " + registrationId);
    }

    return toClientRegistration(dto);
  }

  private ClientRegistration toClientRegistration(RegisteredClientDto dto) {
    Map<String, Object> providerMetadata = new HashMap<>();
    providerMetadata.put("end_session_endpoint", "http://" + TenantContextHolder.getTenant().getDomain() + ":9000/connect/logout");
    return ClientRegistration.withRegistrationId(dto.getClientId())
            .clientId(dto.getClientId())
            .clientSecret(dto.getClientSecret())
            .clientName(dto.getClientName())
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(dto.getRedirectUris()) // You may need to split/comma-separate and pick one
            .scope(dto.getScopes().split(","))
            .issuerUri("http://" + TenantContextHolder.getTenant().getDomain() + ":9000")
            .providerConfigurationMetadata(providerMetadata)
            .authorizationUri("http://" + TenantContextHolder.getTenant().getDomain() + ":9000/oauth2/authorize")
            .tokenUri("http://" + TenantContextHolder.getTenant().getDomain() + ":9000/oauth2/token")
            .jwkSetUri("http://" + TenantContextHolder.getTenant().getDomain() + ":9000/oauth2/jwks")
            .userInfoUri("http://" + TenantContextHolder.getTenant().getDomain() + ":9000/userinfo")
            .userNameAttributeName(IdTokenClaimNames.SUB)
            .build();

  }
}
