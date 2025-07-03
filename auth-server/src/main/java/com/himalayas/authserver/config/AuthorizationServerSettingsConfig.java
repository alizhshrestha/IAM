package com.himalayas.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerSettingsConfig {

  @Bean
  public AuthorizationServerSettings authorizationServerSettings(){
    return AuthorizationServerSettings.builder()
            .multipleIssuersAllowed(true)
            .build();
  }
}
