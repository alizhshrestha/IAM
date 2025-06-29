//package com.himalayas.authserver.service;
//
//import com.himalayas.authserver.model.User;
//import com.himalayas.authserver.repo.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@RequiredArgsConstructor
//public class JpaUserDetailsService implements UserDetailsService {
//
//  private final UserRepository userRepository;
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//    return new org.springframework.security.core.userdetails.User(
//            user.getUsername(),
//            user.getPassword(),
//            user.isEnabled(),
//            true, true, true,
//            user.getRoles().stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                    .toList()
//    );
//  }
//}
