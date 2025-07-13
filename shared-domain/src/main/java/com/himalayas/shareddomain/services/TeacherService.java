package com.himalayas.shareddomain.services;

import com.himalayas.shareddomain.dto.response.TeacherClassDto;
import com.himalayas.shareddomain.mapper.TeacherClassMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final TeacherClassMapper teacherClassMapper;

  public List<TeacherClassDto> getClassesForCurrentTeacher(String teacherId, String tenantId, String schoolId){
    return teacherClassMapper.findClassesForTeacher(teacherId, tenantId, schoolId);
  }
}
