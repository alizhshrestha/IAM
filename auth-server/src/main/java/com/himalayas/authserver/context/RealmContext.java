package com.himalayas.authserver.context;

public class RealmContext {
  private static final ThreadLocal<String> CURRENT_REALM = new ThreadLocal<>();

  public static void setRealm(String realm){
    CURRENT_REALM.set(realm);
  }

  public static String getRealm(){
    return CURRENT_REALM.get();
  }

  public static void clear(){
    CURRENT_REALM.remove();
  }
}
