package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.dto.response.TenantResponseDto;
import com.himalayas.shareddomain.entities.Tenant;
import com.himalayas.shareddomain.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService{

  private final TenantMapper tenantMapper;


  @Override
  @Cacheable(value = "tenants", key = "#issuer")
  public Optional<TenantResponseDto> findByIssuer(String issuer) {
    try{
      String domain = new URI(issuer).getHost();
      return tenantMapper.findByDomain(domain);
    }catch (Exception e) {
      return Optional.empty();
    }
  }
}
