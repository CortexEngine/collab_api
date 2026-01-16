package com.example.collab.service.validation;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.repository.WorkTimeRepository;

@Component
public class WorkTimeValidator {

  WorkTimeRepository workTimeRepository;

  @Autowired
  public WorkTimeValidator(WorkTimeRepository workTimeRepository) {

    this.workTimeRepository = workTimeRepository;

  }

  public void validateTimeEndBeforeIntialTime(LocalTime initialTime, LocalTime endTime, Boolean isOvernight) {

    if (endTime.isBefore(initialTime)) {
      
      if (isOvernight == null || Boolean.FALSE.equals(isOvernight)) {

        throw new IllegalArgumentException("End time cannot be before initial time if not overnight.");

      } 

    } else {

      if (isOvernight != null && Boolean.TRUE.equals(isOvernight)) {

        throw new IllegalArgumentException("End time must be before initial time if overnight.");

      }

    }

  }

  public void validateSameWorkTimes(LocalTime initialTime, LocalTime endTime, LocalTime initialBreakTime, LocalTime endBreakTime) {

    if (workTimeRepository.findByInitialTimeAndEndTime(initialTime, endTime).isPresent()) {

      if (workTimeRepository.findByInitialBreakTimeAndEndBreakTime(initialBreakTime, endBreakTime).isPresent()) {

        throw new IllegalArgumentException("A work time with the same work and break times already exists.");
        
      }
      
    }

  }

  public void validateInitialandEndWorkTimeLimit (LocalTime initialTime, LocalTime endTime){}

  public void validateInitialAndEndBreakTimeLimit(LocalTime initialBreakTime, LocalTime endBreakTime) {}

  public void validateIsActiveWorkTime(Long id) {}

  public void validateRequireAndAutoPunchSameTime(Boolean requiresPunch, Boolean autoGeneratePunches) {}

}
