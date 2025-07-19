package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.response.ClassResponseDto;
import com.himalayas.shareddomain.dto.response.ClassStudentsResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassesMapper {
  @Select("""
            SELECT c.id,
                   c.name,
                   c.section,
                   c.grade,
                   c.school_id,
                   c.tenant_id,
                   c.created_at,
                   c.updated_at,
                   c.created_by,
                   c.updated_by,
                   c.deleted
            FROM classes c
            JOIN schools s ON s.id = c.school_id
            WHERE c.school_id = #{schoolId}
              AND c.deleted = FALSE
              AND s.deleted = FALSE
              AND c.tenant_id = #{tenantId}
            ORDER BY c.name;  
          """)
  List<ClassResponseDto> findAllClassesBySchoolIdAndTenantId(@Param("schoolId") String schoolId,
                                                             @Param("tenantId") String tenantId);

  @Select("""
              SELECT st.id,
                     u.full_name AS name,
                     st.enrollment_no,
                     st.enrollment_date,
                     st.guardian_name,
                     st.guardian_phone,
                     st.address,
                     st.created_at,
                     st.updated_at,
                     st.created_by,
                     st.updated_by,
                     st.deleted
              FROM students st
              JOIN users u ON u.id = st.user_id
              JOIN schools sch ON sch.id = st.school_id
              JOIN classes cls ON cls.id = st.class_id
              JOIN tenants t ON t.id = st.tenant_id
              WHERE sch.id = #{schoolId}
                AND cls.id = #{classId}
                AND t.id = #{tenantId};
          """)
  List<ClassStudentsResponseDto> findAllStudentsOfClassBySchoolIdAndTenantId(@Param("schoolId") String schoolId,
                                                                             @Param("tenantId") String tenantId,
                                                                             @Param("classId") String classId);
}
