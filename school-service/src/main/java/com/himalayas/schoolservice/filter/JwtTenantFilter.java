package com.himalayas.schoolservice.filter;

import com.himalayas.schoolservice.context.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTenantFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication instanceof JwtAuthenticationToken jwtAuth){
      Jwt jwt = jwtAuth.getToken();
      String tenantId = jwt.getClaimAsString("tenant_id");

      if(tenantId != null){
        TenantContext.setCurrentTenant(tenantId);
      }
    }

    try{
      filterChain.doFilter(request, response);
    }finally {
      TenantContext.clear();
    }
  }
}
