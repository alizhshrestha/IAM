package com.himalayas.schoolservice.controller;

import com.himalayas.schoolservice.entity.School;
import com.himalayas.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class SchoolController {

  private final SchoolService schoolService;

  @GetMapping("/public")
  public String publicEndpoint() {
    return "This is a public endpoint.";
  }

  @GetMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<School> getSchools() {
    return schoolService.getAllSchools();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public School createSchool(@RequestBody School school) {
    return schoolService.saveSchool(school);
  }

}
