package com.himalayas.shareddomain.services;

import com.himalayas.shareddomain.dto.request.AttendanceDetailsRequest;
import com.himalayas.shareddomain.dto.response.StudentAttendanceDto;
import com.himalayas.shareddomain.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
  private final AttendanceMapper attendanceMapper;

  public List<StudentAttendanceDto> getAttendanceForClasses(String classId, String schoolId, String teacherId, LocalDate date, String tenantId) throws AccessDeniedException {
    boolean isAuthorized = attendanceMapper.isTeacherAssignedToClass(classId, teacherId);

    if(!isAuthorized){
      throw new AccessDeniedException("Teacher is not assigned to this class");
    }

    return attendanceMapper.findAttendanceOfStudentBySchoolClassAndDate(classId, date, schoolId, tenantId, teacherId);
  }

  @Transactional
  public void saveOrUpdateAttendance(List<AttendanceDetailsRequest> attendanceDetailsRequests){
    for(AttendanceDetailsRequest request: attendanceDetailsRequests){
      int updated = attendanceMapper.updateAttendance(request);
      if(updated == 0){
        if(request.getId() == null || request.getId().isEmpty()){
          request.setId(UUID.randomUUID().toString());
        }
        attendanceMapper.insertAttendance(request);
      }
    }
  }

}
