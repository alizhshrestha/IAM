package com.himalayas.schoolservice.service;

import com.himalayas.schoolservice.mapper.SchoolMapper;
import com.himalayas.shareddomain.entities.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

  private final SchoolMapper schoolMapper;

  public List<School> getSchoolsForTenant(String tenantId){
    return schoolMapper.findByTenantId(tenantId);
  }
}
