package com.himalayas.authserver.service;

import com.himalayas.authserver.context.RealmContext;
import com.himalayas.authserver.model.Realm;
import com.himalayas.authserver.model.User;
import com.himalayas.authserver.repo.RealmRepository;
import com.himalayas.authserver.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealmAwareUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RealmRepository realmRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String currentRealm = RealmContext.getRealm();
    if(currentRealm == null){
      throw new UsernameNotFoundException("No realm found in context");
    }

    Realm realm = realmRepository.findByName(currentRealm)
            .orElseThrow(()-> new UsernameNotFoundException("Realm not found: " + currentRealm));

    User user = userRepository.findByUsernameAndRealm(username, realm)
            .orElseThrow(()-> new UsernameNotFoundException("User not found in realm: " + username));

    Set<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            authorities);
  }
}
