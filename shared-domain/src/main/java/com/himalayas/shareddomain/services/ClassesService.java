package com.himalayas.shareddomain.services;

import com.himalayas.shareddomain.dto.response.ClassResponseDto;
import com.himalayas.shareddomain.dto.response.ClassStudentsResponseDto;
import com.himalayas.shareddomain.mapper.ClassesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassesService {
  private final ClassesMapper classesMapper;

  public List<ClassResponseDto> findAllClassesBySchoolIdAndTenantId(String schoolId, String tenantId){
    return classesMapper.findAllClassesBySchoolIdAndTenantId(schoolId, tenantId);
  }

  public List<ClassStudentsResponseDto> findAllStudentsOfClassBySchoolIdAndTenantId(String schoolId, String tenantId, String classId){
    return classesMapper.findAllStudentsOfClassBySchoolIdAndTenantId(schoolId, tenantId, classId);
  }
}
