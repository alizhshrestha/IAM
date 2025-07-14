package com.himalayas.shareddomain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseDto {
  private String id;
  private String title;
  private String subject;
  private String className;
  private LocalDateTime dueDate;
}
