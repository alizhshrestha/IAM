package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponseDto {
  private String id;
  private String name;
  private String logoUrl;
  private String address;
  private String academicYear;
  private String tenantId;
  private String userId;
  private String role;
}
