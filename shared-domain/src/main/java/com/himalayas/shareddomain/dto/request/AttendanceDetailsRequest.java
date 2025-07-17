package com.himalayas.shareddomain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDetailsRequest {
  private String id;
  private String classId;
  private String studentId;
  private LocalDate date;
  private boolean present;
  private String recordedBy;
  private String remarks;
  private String tenantId;
  private String schoolId;
  private String createdBy;
  private String updatedBy;
}
