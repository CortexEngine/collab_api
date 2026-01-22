package com.example.collab.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.repository.ScheduleRotationRespository;
@Component
public class ScheduleRotationValidator {

  ScheduleRotationRespository scheduleRotationRespository;

  @Autowired
  public ScheduleRotationValidator(ScheduleRotationRespository scheduleRotationRespository){

    this.scheduleRotationRespository = scheduleRotationRespository;

  }

  public void validateMaxDayIndexPerSchedule(Long workScheduleId) {

    Long countWeekDays = scheduleRotationRespository.countByworkScheduleIdAndWorkday(workScheduleId, true);

    if (countWeekDays >= 6) {

      throw new IllegalArgumentException("A schedule cannot have more than 6 work days per week.");
    
    }

  }

  public void validateMinDayIndexPerSchedule(Long workScheduleId) {

    Long countWeekDays = scheduleRotationRespository.countByworkScheduleIdAndWorkday(workScheduleId, false);

    if (countWeekDays <= 1 || countWeekDays >= 3) {

      throw new IllegalArgumentException("A schedule must have at least 1 rest day per week or no more than 3 rest days per week.");
    
    }

  }

  public void validateDayIndexRange(Integer dayIndex) {

    if (dayIndex == null || dayIndex < 1 || dayIndex > 7) {

      throw new IllegalArgumentException("Day index must be between 1 and 7.");
    
    }

  }

  public void validateDuplicateDayIndex(Long workScheduleId, Integer dayIndex) {

    if (scheduleRotationRespository.findByWorkScheduleIdAndDayIndex(workScheduleId, dayIndex).isPresent()) {

      throw new IllegalArgumentException("Day index " + dayIndex + " already exists for this work schedule.");

    }

  }

}
