package com.himalayas.schoolservice.controller;

import com.himalayas.securitycommons.annotation.CurrentUser;
import com.himalayas.securitycommons.user.AuthenticatedUser;
import com.himalayas.shareddomain.dto.response.ClassResponseDto;
import com.himalayas.shareddomain.dto.response.ClassStudentsResponseDto;
import com.himalayas.shareddomain.mapper.ClassesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/school/{schoolId}/class")
@RequiredArgsConstructor
public class ClassesController {

  private final ClassesMapper classesMapper;

  @GetMapping
  public ResponseEntity<List<ClassResponseDto>> getAllClassesFromSchoolAndTenant(@CurrentUser AuthenticatedUser user,
                                                                                 @PathVariable String schoolId){
    String tenantId = user.getTenantId();
    return ResponseEntity.ok(classesMapper.findAllClassesBySchoolIdAndTenantId(schoolId, tenantId));
  }

  @GetMapping("/{classId}/students")
  public ResponseEntity<List<ClassStudentsResponseDto>> getAllStudentsOfClassFromSchoolAndTenant(@CurrentUser AuthenticatedUser user,
                                                                                                 @PathVariable String schoolId,
                                                                                                 @PathVariable String classId){
    String tenantId = user.getTenantId();
    return ResponseEntity.ok(classesMapper.findAllStudentsOfClassBySchoolIdAndTenantId(schoolId, tenantId, classId));
  }
}
