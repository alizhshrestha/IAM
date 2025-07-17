package com.himalayas.schoolservice.controller;

import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import com.himalayas.shareddomain.dto.request.AttendanceDetailsRequest;
import com.himalayas.shareddomain.dto.response.StudentAttendanceDto;
import com.himalayas.shareddomain.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

  private final AttendanceService attendanceService;

  @GetMapping
  public ResponseEntity<List<StudentAttendanceDto>> getAttendance(
          @RequestParam String classId,
          @RequestParam String schoolId,
          @RequestParam String teacherId,
          @RequestParam LocalDate date,
          @CurrentUser AuthenticatedUser user
          ) throws AccessDeniedException {
    String tenantId = user.getTenantId();
    System.out.println("Date is : " + date.toString());
    List<StudentAttendanceDto> attendanceList = attendanceService.getAttendanceForClasses(classId, schoolId, teacherId, date, tenantId);
    return ResponseEntity.ok(attendanceList);
  }

  @PostMapping
  public ResponseEntity<Void> saveAttendance(@RequestBody List<AttendanceDetailsRequest> attendanceList){
    attendanceService.saveOrUpdateAttendance(attendanceList);
    return ResponseEntity.ok().build();
  }
}
