package com.himalayas.authserver.filter;

import com.himalayas.authserver.context.TenantContext;
import com.himalayas.authserver.entity.Tenant;
import com.himalayas.authserver.repository.TenantRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantFilter implements Filter{

  private final TenantRepository tenantRepository;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String serverName = request.getServerName();
    log.debug("Filtering request for serverName: {}", serverName);

    String tenantIdentifier = null;
    if (serverName.contains(".")) {
      tenantIdentifier = serverName.substring(0, serverName.indexOf("."));
    } else {
      if ("localhost".equalsIgnoreCase(serverName) || "127.0.0.1".equals(serverName)) {
        log.warn("No subdomain found. Attempting to use 'default' tenant for localhost/127.0.0.1.");
        tenantIdentifier = "default";
      }
    }

    if (tenantIdentifier != null && !tenantIdentifier.isEmpty()) {
      Optional<Tenant> tenant = tenantRepository.findById(tenantIdentifier);
      if(tenant.isPresent()){
        TenantContext.setCurrentTenant(tenant.get().getId());
        log.debug("Tenant '{}' set in context for request to {}", tenant.get().getId(), serverName);
        try{
          filterChain.doFilter(servletRequest, servletResponse);
        }finally {
          TenantContext.clear(); // Always clear the context
          log.debug("Tenant context cleared.");
        }
        return;
      }else {
        log.warn("Tenant with ID '{}' not found for serverName: {}", tenantIdentifier, serverName);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or unknown tenant.");
        return; // Stop processing and return error
      }
    }
    log.warn("Could not determine tenant from serverName: {}", serverName);
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tenant could not be determined from request.");
  }
}
