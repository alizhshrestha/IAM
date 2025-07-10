package com.himalayas.schoolservice.mapper;

import com.himalayas.schoolservice.dto.SchoolDto;
import com.himalayas.shareddomain.entities.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper {

  @Select("SELECT * FROM schools WHERE tenant_id = #{tenantId}")
  List<School> findByTenantId(@Param("tenantId") String tenantId);

  @Select("""
              SELECT s.id, s.name, s.logo_url, s.address, s.academic_year, s.tenant_id, r.name AS role
              FROM schools s 
                  JOIN users u
                      ON s.id = u.school_id
                  JOIN user_roles ur
                      ON u.id = ur.user_id
                  JOIN roles r
                      ON ur.role_id = r.id
              WHERE s.tenant_id = #{tenantId} AND u.app_user_id = #{appUserId}
          """)
  List<SchoolDto> findUserSchools(@Param("tenantId") String tenantId, @Param("appUserId") String appUserId);
}
