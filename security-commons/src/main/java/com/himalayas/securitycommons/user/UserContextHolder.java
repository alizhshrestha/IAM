package com.himalayas.securitycommons.user;

import java.util.List;

public class UserContextHolder {
  private static final ThreadLocal<AuthenticatedUser> CONTEXT = new ThreadLocal<>();

  public static void setUser(AuthenticatedUser user){
    CONTEXT.set(user);
  }

  public static AuthenticatedUser getUser(){
    return CONTEXT.get();
  }

  public static void clear(){
    CONTEXT.remove();
  }

  public static boolean hasRole(String role){
    AuthenticatedUser user = getUser();
    return user != null && user.getRoles() != null && user.getRoles().contains(role);
  }
}
