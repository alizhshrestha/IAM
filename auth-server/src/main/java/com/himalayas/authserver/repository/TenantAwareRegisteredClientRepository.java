package com.himalayas.authserver.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.himalayas.authserver.context.TenantContext;
import com.himalayas.authserver.converter.RegisteredClientConverter;
import com.himalayas.authserver.dto.RegisteredClientDto;
import com.himalayas.authserver.mapper.TenantAwareRegisteredClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Slf4j
public class TenantAwareRegisteredClientRepository implements RegisteredClientRepository {

  private final TenantAwareRegisteredClientMapper mapper;

  @Override
  public void save(RegisteredClient registeredClient) {
    String tenantId = TenantContext.getCurrentTenant();
    RegisteredClientDto entity;
    try {
      entity = RegisteredClientConverter.from(registeredClient, tenantId);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    mapper.save(entity);
  }

  @Override
  public RegisteredClient findById(String id) {
    String tenantId = TenantContext.getCurrentTenant();
    RegisteredClientDto entity = mapper.findById(id, tenantId);
    try {
      return entity != null ? RegisteredClientConverter.toRegisteredClient(entity) : null;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    String tenantId = TenantContext.getCurrentTenant();
    log.info("Looking up clientId={} for tenantId={}", clientId, tenantId);
    RegisteredClientDto entity = mapper.findByClientId(clientId, tenantId);
    if (entity == null) {
      log.warn("No RegisteredClient found for clientId={} in tenant={}", clientId, tenantId);
      return null;
    } else {
      try {
        return RegisteredClientConverter.toRegisteredClient(entity);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
