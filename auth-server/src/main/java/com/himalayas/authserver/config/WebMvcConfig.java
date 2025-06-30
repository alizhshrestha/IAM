package com.himalayas.authserver.config;

import com.himalayas.authserver.interceptor.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final TenantInterceptor tenantInterceptor;

  /**
   * Adds the TenantInterceptor to the interceptor registry,
   * ensuring it runs for every incoming request.
   * @param registry The InterceptorRegistry to add the interceptor to.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tenantInterceptor);
  }
}
