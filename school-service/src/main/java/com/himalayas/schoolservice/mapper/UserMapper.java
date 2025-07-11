package com.himalayas.schoolservice.mapper;

import com.himalayas.schoolservice.dto.request.UserWithRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
  @Select("""
          SELECT u.id, u.full_name, u.email, r.name as role
          FROM users u
          JOIN schools s ON u.school_id = s.id
          LEFT JOIN user_roles ur ON u.id = ur.user_id
          JOIN roles r ON ur.role_id = r.id
          WHERE s.tenant_id = #{tenantId}
            AND s.id = #{schoolId}
          """)
  List<UserWithRoleDto> findUsersBySchool(
          @Param("tenantId") String tenantId,
          @Param("schoolId") String schoolId
  );
}
