package com.himalayas.authserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

  private final AppUser appUser;

  public CustomUserDetails(AppUser appUser, Collection<? extends GrantedAuthority> authorities) {
    super(appUser.getUsername(), appUser.getPassword(), authorities);
    this.appUser = appUser;
  }

  public String getTenantId(){
    return appUser.getTenantId();
  }

  public AppUser getAppUser(){
    return appUser;
  }
}
