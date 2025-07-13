package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.response.TeacherClassDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherClassMapper {
  @Select("""
              SELECT cs.id       AS subject_id,
                     cls.id      AS id,
                     cls.name    AS name,
                     cls.grade   AS grade,
                     cls.section AS section,
                     sub.name    AS subject
              FROM   class_subjects cs
                     JOIN classes cls
                       ON cs.class_id = cls.id
                     JOIN subjects sub
                       ON cs.subject_id = sub.id
              WHERE  cs.teacher_id = #{teacherId}
                     AND cls.tenant_id = #{tenantId}
                     AND cls.school_id = #{schoolId}
                     AND cls.deleted = false;
          """)
  List<TeacherClassDto> findClassesForTeacher(
          @Param("teacherId") String teacherId,
          @Param("tenantId") String tenantId,
          @Param("schoolId") String schoolId
  );
}
