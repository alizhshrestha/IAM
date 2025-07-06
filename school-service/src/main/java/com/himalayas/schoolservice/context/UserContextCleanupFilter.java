package com.himalayas.schoolservice.context;

import com.himalayas.securitycommons.tenant.TenantContextHolder;
import com.himalayas.securitycommons.user.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserContextCleanupFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    }finally {
      UserContextHolder.clear();
      TenantContextHolder.clear();
    }
  }
}
