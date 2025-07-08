package com.himalayas.authserver.service;

import com.himalayas.authserver.context.TenantContext;
import com.himalayas.authserver.repository.AppUserRepository;
import com.himalayas.shareddomain.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantAwareUserDetailsService implements UserDetailsService {

  private final AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    String tenantId = TenantContext.getCurrentTenant();

    Assert.notNull(tenantId, "Tenant ID must be set in the current context for user authentication.");

    AppUser appUser = appUserRepository.findByUsernameAndTenantId(username, tenantId)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found for tenant '" + tenantId + "': " + username));

    List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream()
            .map(SimpleGrantedAuthority::new)
            .toList();

    return new CustomUserDetails(appUser, authorities);
  }
}

