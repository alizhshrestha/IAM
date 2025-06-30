package com.himalayas.authserver.context;

/**
 * A ThreadLocal-based context holder for the current tenant ID.
 * This allows the tenant ID to be accessible throughout the request processing
 * without explicitly passing it through method parameters.
 */
public class TenantContext {
  private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

  /**
   * Sets the current tenant ID for the current thread.
   * @param tenantId The ID of the tenant.
   */
  public static void setCurrentTenant(String tenantId) {
    currentTenant.set(tenantId);
  }

  /**
   * Retrieves the current tenant ID for the current thread.
   * @return The current tenant ID, or null if not set.
   */
  public static String getCurrentTenant(){
    return currentTenant.get();
  }

  /**
   * Clears the current tenant ID from the ThreadLocal.
   * This is important to prevent memory leaks in thread pools (e.g., web servers).
   */
  public static void clear(){
    currentTenant.remove();
  }
}
