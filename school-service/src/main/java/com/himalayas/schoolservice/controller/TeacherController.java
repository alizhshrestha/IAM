package com.himalayas.schoolservice.controller;

import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import com.himalayas.shareddomain.dto.response.TeacherClassDto;
import com.himalayas.shareddomain.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/school/{schoolId}/teacher")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping("/{teacherId}/classes")
  public ResponseEntity<List<TeacherClassDto>> getTeacherClasses(@CurrentUser AuthenticatedUser user,
                                                @PathVariable("schoolId") String schoolId,
                                                @PathVariable("teacherId") String teacherId) {
    String tenantId = user.getTenantId();
    return ResponseEntity.ok(teacherService.getClassesForCurrentTeacher(teacherId, tenantId, schoolId));
  }
}
