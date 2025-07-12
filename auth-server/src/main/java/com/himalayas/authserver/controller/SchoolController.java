package com.himalayas.authserver.controller;

import com.himalayas.shareddomain.dto.request.SchoolDto;
import com.himalayas.shareddomain.dto.response.SchoolClientsDto;
import com.himalayas.shareddomain.services.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SchoolController {

  private final SchoolService schoolService;

  @GetMapping("/public/api/schools")
  public List<SchoolClientsDto> getSchools(){
    return schoolService.findAllSchoolsInfo();
  }
}
