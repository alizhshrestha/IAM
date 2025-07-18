package com.himalayas.authserver.filter;

import com.himalayas.securitycommons.tenant.TenantContextHolder;
import com.himalayas.securitycommons.user.UserContextHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomClearingLogoutFilter implements Filter{
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    String requestUri = request.getRequestURI();
    boolean isLogoutRequest = requestUri.contains("/public");

    if(isLogoutRequest){
      log.debug("Custom ContextClearingLogoutFilter: Intercepted potential logout request for URI: {}", requestUri);
      UserContextHolder.clear();
      TenantContextHolder.clear(); // Always clear the context
      log.debug("Tenant context and user context cleared.");
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
