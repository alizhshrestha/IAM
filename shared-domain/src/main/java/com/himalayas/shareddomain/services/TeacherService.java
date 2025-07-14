package com.himalayas.shareddomain.services;

import com.himalayas.shareddomain.dto.request.TeacherScheduleSlotDto;
import com.himalayas.shareddomain.dto.response.AssignmentResponseDto;
import com.himalayas.shareddomain.dto.response.TeacherClassDto;
import com.himalayas.shareddomain.dto.response.TeacherScheduleResponseDto;
import com.himalayas.shareddomain.enums.WeekDays;
import com.himalayas.shareddomain.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final TeacherMapper teacherClassMapper;

  public List<TeacherClassDto> getClassesForCurrentTeacher(String teacherId, String tenantId, String schoolId) {
    return teacherClassMapper.findClassesForTeacher(teacherId, tenantId, schoolId);
  }

  public TeacherScheduleResponseDto getTeacherSchedule(String teacherId, String tenantId, String schoolId) {
    List<TeacherScheduleSlotDto> slots = teacherClassMapper.findTeacherSchedule(teacherId, tenantId, schoolId);
    Set<String> timeSlots = new TreeSet<>();
    Set<String> days = Arrays.stream(WeekDays.values())
            .map(WeekDays::getValue)
            .collect(Collectors.toCollection(LinkedHashSet::new));

    Map<String, Map<String, String>> schedule = new TreeMap<>();
    for(TeacherScheduleSlotDto slot: slots){
      timeSlots.add(slot.getTimeSlot());
      schedule
              .computeIfAbsent(slot.getTimeSlot(), k -> new HashMap<>())
              .put(slot.getDayOfWeek(), slot.getClassName() + "," + slot.getSubjectName());
    }

    return new TeacherScheduleResponseDto(
            new ArrayList<>(timeSlots),
            new ArrayList<>(days),
            schedule
    );
  }

  public List<AssignmentResponseDto> getAssignmentsForTeacher(String teacherId, String tenantId, String schoolId){
    return teacherClassMapper.findTeacherAssignment(teacherId, tenantId, schoolId);
  }
}
