package com.himalayas.authserver.mapper;

import com.himalayas.authserver.dto.RegisteredClientDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TenantAwareRegisteredClientMapper {

  @Insert("""
    INSERT INTO registered_client (
      id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
      client_name, client_authentication_methods, authorization_grant_types,
      redirect_uris, scopes, client_settings, token_settings, tenant_id
    ) VALUES (
      #{id}, #{clientId}, #{clientIdIssuedAt}, #{clientSecret}, #{clientSecretExpiresAt},
      #{clientName}, #{clientAuthenticationMethods}, #{authorizationGrantTypes},
      #{redirectUris}, #{scopes}, #{clientSettings}, #{tokenSettings}, #{tenantId}
    )
    ON DUPLICATE KEY UPDATE
      client_secret = VALUES(client_secret),
      redirect_uris = VALUES(redirect_uris),
      scopes = VALUES(scopes),
      client_settings = VALUES(client_settings),
      token_settings = VALUES(token_settings)
    """)
  void save(RegisteredClientDto client);

  @Select("SELECT * FROM registered_client WHERE id = #{id} AND tenant_id = #{tenantId}")
  RegisteredClientDto findById(@Param("id") String id, @Param("tenantId") String tenantId);

  @Select("SELECT * FROM registered_client WHERE client_id = #{clientId} AND tenant_id = #{tenantId}")
  RegisteredClientDto findByClientId(@Param("clientId") String clientId, @Param("tenantId") String tenantId);
}
