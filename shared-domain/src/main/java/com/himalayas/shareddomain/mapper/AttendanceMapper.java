package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.request.AttendanceDetailsRequest;
import com.himalayas.shareddomain.dto.response.StudentAttendanceDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {

  @Select("""
          SELECT a.student_id AS student_id, u.full_name AS student_name , a.present AS present
          FROM attendances a
                   JOIN students s ON s.id = a.student_id
                   JOIN users u ON u.id = s.user_id
                   JOIN user_roles ur
                        ON ur.user_id = u.id
                   JOIN roles r ON r.id = ur.role_id and r.name = 'ROLE_STUDENT'
          WHERE a.tenant_id = #{tenantId}
            AND u.active = true
            AND a.class_id = #{classId}
            AND a.date = #{date}
            AND u.deleted = false
            AND a.recorded_by = #{teacherId};
          """)
  List<StudentAttendanceDto> findAttendanceOfStudentBySchoolClassAndDate(
          @Param("classId") String classId,
          @Param("date") LocalDate date,
          @Param("schoolId") String schoolId,
          @Param("tenantId") String tenantId,
          @Param("teacherId") String teacherId

  );

  @Select("""
              SELECT COUNT(*) FROM class_subjects
              WHERE class_id = #{classId}
              AND teacher_id = #{teacherId};
          """)
  boolean isTeacherAssignedToClass(@Param("classId") String classId, @Param("teacherId") String teacherId);

  /*@Update("""
        UPDATE attendances
        SET present = #{present},
        updated_by = #{updatedBy},
        updated_at = NOW()
        WHERE class_id = #{classId}
        AND student_id = #{studentId}
        AND date = #{date};
    """)
  int updateAttendance(@Param("present") boolean present,
                       @Param("updatedBy") String updatedBy,
                       @Param("classId") String classId,
                       @Param("studentId") String studentId,
                       @Param("date") LocalDate date);*/

  @Update("""
              UPDATE attendances
              SET present = #{present},
              updated_by = #{updatedBy},
              updated_at = NOW()
              WHERE class_id = #{classId}
              AND student_id = #{studentId}
              AND date = #{date};
          """)
  int updateAttendance(AttendanceDetailsRequest attendanceDetailsRequest);


  @Insert("""
              INSERT INTO attendances (id, class_id, student_id, date, present, recorded_by, remarks, tenant_id, school_id,
                                       created_by, updated_by)
              VALUES (#{id}, #{classId}, #{studentId}, #{date}, #{present}, #{recordedBy},#{remarks},#{tenantId}, #{schoolId}, #{createdBy}, #{updatedBy});
          """)
  int insertAttendance(AttendanceDetailsRequest attendanceDetailsRequest);

}
