package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentsResponseDto {
  private String id;
  private String name;
  private String enrollmentNo;
  private LocalDateTime enrollmentDate;
  private String guardianName;
  private String guardianPhone;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String updatedBy;
  private boolean deleted;
}
