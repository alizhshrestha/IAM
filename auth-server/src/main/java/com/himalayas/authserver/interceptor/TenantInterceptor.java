package com.himalayas.authserver.interceptor;

import com.himalayas.authserver.context.TenantContext;
import com.himalayas.authserver.entity.Tenant;
import com.himalayas.authserver.repository.TenantRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

  private final TenantRepository tenantRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String serverName = request.getServerName();
    log.debug("Intercepting request for serverName: {}", serverName);

    String tenantIdentifier = null;
    if(serverName.contains(".")){
      tenantIdentifier = serverName.substring(0, serverName.indexOf("."));
    }else{
      if("localhost".equalsIgnoreCase(serverName) || "127.0.0.1".equals(serverName)){
        log.warn("No subdomain found. Attempting to use 'default' tenant for localhost/127.0.0.1.");
        tenantIdentifier = "default";
      }
    }

    if(tenantIdentifier != null && !tenantIdentifier.isEmpty()){
      Optional<Tenant> tenant = tenantRepository.findById(tenantIdentifier);
      if(tenant.isPresent()){
        TenantContext.setCurrentTenant(tenant.get().getId());
        log.debug("Tenant '{}' set in context for request to {}", tenant.get().getId(), serverName);
        return true;
      }else{
        log.warn("Tenant with ID '{}' not found for serverName: {}", tenantIdentifier, serverName);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return false;
      }
    }
    log.warn("Could not determine tenant from serverName: {}", serverName);
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tenant could not be determined from request.");
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    // Clear the tenant ID from ThreadLocal after the request is processed
    TenantContext.clear();
    log.debug("Tenant context cleared.");
  }
}
