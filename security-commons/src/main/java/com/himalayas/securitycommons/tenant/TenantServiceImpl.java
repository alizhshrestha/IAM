package com.himalayas.securitycommons.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService{

  private final TenantRepository tenantRepository;


  @Override
  public Optional<Tenant> findByDomain(String domain) {
    return tenantRepository.findByDomainIgnoreCase(domain);
  }

  @Override
  @Cacheable("tenants")
  public Optional<Tenant> findByIssuer(String issuer) {
    try{
      String host = new URI(issuer).getHost();
      return tenantRepository.findByDomainIgnoreCase(host);
    }catch (Exception e) {
      return Optional.empty();
    }
  }



}
