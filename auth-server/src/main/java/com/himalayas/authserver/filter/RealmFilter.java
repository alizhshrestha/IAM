package com.himalayas.authserver.filter;

import com.himalayas.authserver.context.RealmContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RealmFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String path = request.getRequestURI();
    String[] parts = path.split("/");

    if(parts.length > 2 && "realms".equals(parts[1])){
      String realm = parts[2];
      RealmContext.setRealm(realm);
    }

    try{
      filterChain.doFilter(request, response);
    }finally {
      RealmContext.clear();
    }
  }
}
