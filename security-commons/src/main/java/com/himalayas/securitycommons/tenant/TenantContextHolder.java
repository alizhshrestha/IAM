package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.entities.Tenant;

public class TenantContextHolder {

  private static final ThreadLocal<Tenant> CONTEXT = new ThreadLocal<>();

  public static void setTenant(Tenant tenantId){
    CONTEXT.set(tenantId);
  }

  public static Tenant getTenant(){
    return CONTEXT.get();
  }

  public static void clear(){
    CONTEXT.remove();
  }

  public static String getTenantId(){
    Tenant tenant = CONTEXT.get();
    return tenant != null ? tenant.getId() : null;
  }
}
