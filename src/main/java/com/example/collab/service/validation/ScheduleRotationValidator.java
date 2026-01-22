package com.example.collab.service.validation;

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

  public void validateMaxDayIndexPerSchedule(Long workScheduleId) {

    Long countWeekDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, true);

    if (countWeekDays >= 6) {

      throw new IllegalArgumentException("A schedule cannot have more than 6 work days per week.");
    
    }

  }

  public void validateMinDayIndexPerSchedule(Long workScheduleId) {

    Long countWeekDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, false);

    if (countWeekDays <= 1 || countWeekDays >= 3) {

      throw new IllegalArgumentException("A schedule must have at least 1 rest day per week or no more than 3 rest days per week.");
    
    }

  }

  public void validateTotalDaysMatchSchedule(Long workScheduleId) {

    WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId).orElseThrow(() -> new IllegalArgumentException("Work schedule not found"));
    
    Integer expectedTotalDays = workSchedule.getWorkDaysPerWeek() + workSchedule.getRestDaysPerWeek();

    Long actualTotalDays = scheduleRotationRepository.countByWorkScheduleId(workScheduleId);
    
    if (!actualTotalDays.equals(expectedTotalDays.longValue())) {

      throw new IllegalArgumentException("Schedule requires " + expectedTotalDays + " days but has " + actualTotalDays + " rotations configured.");

    }

  }

  public void validateDuplicateDayIndex(Long workScheduleId, Integer dayIndex) {

    if (scheduleRotationRepository.findByWorkScheduleIdAndDayIndex(workScheduleId, dayIndex).isPresent()) {

      throw new IllegalArgumentException("Day index " + dayIndex + " already exists for this work schedule.");

    }

  }

}
