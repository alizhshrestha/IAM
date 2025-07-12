package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.request.SchoolDto;
import com.himalayas.shareddomain.dto.response.SchoolResponseDto;
import com.himalayas.shareddomain.entities.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper {
  @Select("""
          select distinct s.id             as school_id,
                          s.name           as school_name,
                          t.id             as tenant_id,
                          t.domain         as tenant_domain,
                          rc.client_id     as client_id,
                          rc.client_name   as client_name,
                          rc.redirect_uris as redirect_uri,
                          rc.scopes        as scopes
          from schools s
                   join tenants t
                        on s.tenant_id = t.id
                   join registered_client rc on rc.tenant_id = t.id;
          """)
  List<SchoolDto> findAllSchoolsInfo();

  @Select("SELECT * FROM schools WHERE tenant_id = #{tenantId}")
  List<School> findByTenantId(@Param("tenantId") String tenantId);

  @Select("""
              SELECT s.id, s.name, s.logo_url, s.address, s.academic_year, s.tenant_id, r.name AS role, u.id AS userId
              FROM schools s
                  JOIN users u
                      ON s.id = u.school_id
                  JOIN user_roles ur
                      ON u.id = ur.user_id
                  JOIN roles r
                      ON ur.role_id = r.id
              WHERE s.tenant_id = #{tenantId} AND u.app_user_id = #{appUserId}
          """)
  List<SchoolResponseDto> findUserSchools(@Param("tenantId") String tenantId, @Param("appUserId") String appUserId);
}
