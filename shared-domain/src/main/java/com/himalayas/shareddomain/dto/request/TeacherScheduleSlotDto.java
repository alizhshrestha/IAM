package com.himalayas.shareddomain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherScheduleSlotDto {
  private String timeSlot;       // e.g., "08:00-09:00"
  private String dayOfWeek;      // e.g., "MONDAY"
  private String className;      // e.g., "Grade 1 - A"
  private String subjectName;    // e.g., "Math"
}
