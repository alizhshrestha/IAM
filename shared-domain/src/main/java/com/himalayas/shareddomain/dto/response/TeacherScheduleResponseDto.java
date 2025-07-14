package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherScheduleResponseDto {
  private List<String> timeSlots;
  private List<String> days;
  private Map<String, Map<String, String>> schedule;
}
