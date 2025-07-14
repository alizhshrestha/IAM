package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.request.TeacherScheduleSlotDto;
import com.himalayas.shareddomain.dto.response.AssignmentResponseDto;
import com.himalayas.shareddomain.dto.response.TeacherClassDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
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

  @Select("""
              SELECT
                  s.time_slot,
                  s.day_of_week,
                  c.name AS class_name,
                  subj.name AS subject_name
                  FROM
                      class_schedule s
                  JOIN class_subjects cs ON s.class_subject_id = cs.id
                  JOIN classes c ON cs.class_id = c.id
                  JOIN subjects subj ON cs.subject_id = subj.id
                  WHERE
                      cs.teacher_id = #{teacherId}
                      AND s.tenant_id = #{tenantId}
                      AND s.school_id = #{schoolId}
                  ORDER BY
                      s.time_slot, s.day_of_week;
          """)
  List<TeacherScheduleSlotDto> findTeacherSchedule(
          @Param("teacherId") String teacherId,
          @Param("tenantId") String tenantId,
          @Param("schoolId") String schoolId
  );

  @Select("""
          SELECT a.id,
                 a.title,
                 s.name AS subject,
                 CONCAT(c.name, ' - ', c.section) AS className,
                 a.due_date AS dueDate
          FROM assignments a
                   JOIN class_subjects csub ON a.class_subject_id = csub.id
                   JOIN subjects s ON csub.subject_id = s.id
                   JOIN classes c ON csub.class_id = c.id
          WHERE csub.teacher_id = #{teacherId}
            AND c.school_id = #{schoolId}
            AND c.tenant_id = #{tenantId}
            AND a.deleted = FALSE
          ORDER BY a.due_date ASC;
          """)
  List<AssignmentResponseDto> findTeacherAssignment(
          @Param("teacherId") String teacherId,
          @Param("tenantId") String tenantId,
          @Param("schoolId") String schoolId
  );

}
