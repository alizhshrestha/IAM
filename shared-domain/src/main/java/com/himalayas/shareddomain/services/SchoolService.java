package com.himalayas.shareddomain.services;

import com.himalayas.shareddomain.dto.request.SchoolDto;
import com.himalayas.shareddomain.dto.response.SchoolClientsDto;
import com.himalayas.shareddomain.entities.School;
import com.himalayas.shareddomain.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {

  private final SchoolMapper schoolMapper;

//  @Transactional
  public List<SchoolClientsDto> findAllSchoolsInfo(){
    List<SchoolDto> allSchoolsInfo = schoolMapper.findAllSchoolsInfo();
    Map<String, SchoolClientsDto> schoolsMap = new HashMap<>();
    for(SchoolDto schoolDto: allSchoolsInfo){
      String schoolId = schoolDto.getSchoolId();
      if(!schoolsMap.containsKey(schoolId)){
        SchoolClientsDto clientsDto = SchoolClientsDto.builder()
                .schoolId(schoolId)
                .schoolName(schoolDto.getSchoolName())
                .tenantId(schoolDto.getTenantId())
                .tenantDomain(schoolDto.getTenantDomain())
                .clients(new ArrayList<>(List.of(SchoolClientsDto.ClientDto.builder()
                        .clientId(schoolDto.getClientId())
                        .clientName(schoolDto.getClientName())
                        .redirectUri(schoolDto.getRedirectUri())
                        .scopes(schoolDto.getScopes())
                        .build())))
                .build();
        schoolsMap.put(schoolId, clientsDto);
      }else{
        SchoolClientsDto existingSchool = schoolsMap.get(schoolId);
        existingSchool.getClients().add(SchoolClientsDto.ClientDto.builder()
                .clientId(schoolDto.getClientId())
                .clientName(schoolDto.getClientName())
                .redirectUri(schoolDto.getRedirectUri())
                .scopes(schoolDto.getScopes())
                .build());
      }
    }

    return new ArrayList<>(schoolsMap.values());
  }

  public List<School> getSchoolsForTenant(String tenantId){
    return schoolMapper.findByTenantId(tenantId);
  }
}
