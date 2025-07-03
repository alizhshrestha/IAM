package com.himalayas.schoolservice.service;

import com.himalayas.schoolservice.context.TenantContext;
import com.himalayas.schoolservice.entity.School;
import com.himalayas.schoolservice.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolService {

  private final SchoolRepository schoolRepository;

  public List<School> getAllSchools(){
    return schoolRepository.findAllByTenantId(TenantContext.getCurrentTenant());
  }

  public School saveSchool(School school) {
    school.setId(UUID.randomUUID().toString());
    school.setTenantId(TenantContext.getCurrentTenant());
    return schoolRepository.save(school);
  }
}
