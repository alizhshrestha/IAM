package com.himalayas.schoolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {
  private String id;
  private String name;
  private String logoUrl;
  private String address;
  private String academicYear;
  private String tenantId;
}
