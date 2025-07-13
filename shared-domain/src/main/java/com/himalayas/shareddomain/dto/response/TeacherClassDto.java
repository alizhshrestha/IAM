package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassDto {
  private String subjectId;
  private String id;
  private String name;
  private String grade;
  private String section;
  private String subject;
}
