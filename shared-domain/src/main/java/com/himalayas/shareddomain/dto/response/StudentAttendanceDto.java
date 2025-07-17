package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceDto {
  private String studentId;
  private String studentName;
  private boolean present;
}
