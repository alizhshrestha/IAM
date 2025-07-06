package com.himalayas.securitycommons.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
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
  @Cacheable(value = "tenants", key = "#issuer")
  public Optional<Tenant> findByIssuer(String issuer) {
    try{
      String host = new URI(issuer).getHost();
      return tenantRepository.findByDomainIgnoreCase(host);
    }catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<Tenant>> findAll(){
    return Optional.of(tenantRepository.findAll());
  }



}
