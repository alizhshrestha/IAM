package com.himalayas.authserver.mapper;

import com.himalayas.authserver.dto.ClientDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientMapper {

  @Select("""
              SELECT c.client_id, c.redirect_uris, t.id AS tenantId, c.scopes, c.client_name
              FROM registered_client c
                       JOIN tenants t ON c.tenant_id = t.id
              WHERE t.id = #{tenantId};
          """)
  List<ClientDto> findClientsByTenantId(@Param("tenantId") String tenantId);
}
