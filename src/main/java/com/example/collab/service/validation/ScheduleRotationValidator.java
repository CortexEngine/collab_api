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

  void validateMaxDayIndexPerSchedule(Long workScheduleId) {

    Long countWeekDays = scheduleRotationRespository.countByworkScheduleIdAndWorkday(workScheduleId, true);

    if (countWeekDays >= 6) {

      throw new IllegalArgumentException("A schedule cannot have more than 6 work days per week.");
    
    }

  }
}
