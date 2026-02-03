package com.example.collab.service.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.repository.*;
@Component
public class ScheduleRotationValidator {

  ScheduleRotationRepository scheduleRotationRepository;
  WorkScheduleRepository workScheduleRepository;

  @Autowired
  public ScheduleRotationValidator(ScheduleRotationRepository scheduleRotationRepository, WorkScheduleRepository workScheduleRepository) {

    this.scheduleRotationRepository = scheduleRotationRepository;
    this.workScheduleRepository = workScheduleRepository;

  }

  public void validateMaxDayIndexPerSchedule(Long workScheduleId, List<Boolean> workdays) {

    Long countWeekDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, true);

    long newWeekDays = workdays == null ? 0 : workdays.stream().filter(Boolean.TRUE::equals).count();

    if (countWeekDays + newWeekDays > 6) {

      throw new IllegalArgumentException("A schedule cannot have more than 6 work days per week.");

    }

  }

  public void validateMinDayIndexPerSchedule(Long workScheduleId, List<Boolean> workdays) {

    Long countWeekDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, false);

    long newRestDays = workdays == null ? 0 : workdays.stream().filter(Boolean.FALSE::equals).count();

    long totalRestDays = countWeekDays + newRestDays;

    if (totalRestDays <= 1 || totalRestDays >= 3) {

      throw new IllegalArgumentException("A schedule must have at least 1 rest day per week or no more than 3 rest days per week.");

    }

  }

  public void validateTotalDaysMatchSchedule(Long workScheduleId, List<Integer> dayIndexs) {

    WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId).orElseThrow(() -> new IllegalArgumentException("Work schedule not found"));

    Integer expectedTotalDays = workSchedule.getWorkDaysPerWeek() + workSchedule.getRestDaysPerWeek();

    Long actualTotalDays = scheduleRotationRepository.countByWorkScheduleId(workScheduleId);

    long newTotalDays = dayIndexs == null ? 0 : dayIndexs.size();

    if (!Long.valueOf(actualTotalDays + newTotalDays).equals(expectedTotalDays.longValue())) {

      throw new IllegalArgumentException("Schedule requires " + expectedTotalDays + " days but has " + (actualTotalDays + newTotalDays) + " rotations configured.");

    }

  }

  public void validateDuplicateDayIndex(Long workScheduleId, List<Integer> dayIndexs) {

    if (dayIndexs == null || dayIndexs.isEmpty()) {
      return;
    }

    Set<Integer> seen = new HashSet<>();

    for (Integer dayIndex : dayIndexs) {

      if (!seen.add(dayIndex)) {
        throw new IllegalArgumentException("Day index " + dayIndex + " is duplicated in request.");
      }

      if (scheduleRotationRepository.findByWorkScheduleIdAndDayIndex(workScheduleId, dayIndex).isPresent()) {
        throw new IllegalArgumentException("Day index " + dayIndex + " already exists for this work schedule.");
      }

    }

  }

  public void validateDayIndexesAndWorkdaysSize(List<Integer> dayIndexs, List<Boolean> workdays) {

    if (dayIndexs == null || workdays == null) {
      return;
    }

    if (dayIndexs.size() != workdays.size()) {
      throw new IllegalArgumentException("Day indexes and workdays must have the same size.");
    }

  }

}
