package com.example.collab.service.validation;

import java.time.*;

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

  public void validateInitialandEndWorkTimeLimit (LocalTime initialTime, LocalTime endTime, Boolean isOvernight) {

    Long totalHors;

    if (isOvernight != null && Boolean.TRUE.equals(isOvernight)) {

      totalHors = 24 - Duration.between(initialTime, endTime).toHours();

    } else {

      totalHors = Duration.between(initialTime, endTime).toHours();
      
    }

    if(totalHors < 1 || totalHors > 13){

      throw new IllegalArgumentException("Total work time must be between 1 and 13 hours.");

    }
    
  }

  public void validateInitialAndEndBreakTimeLimit(LocalTime initialBreakTime, LocalTime endBreakTime, Boolean isOvernight) {

    Long totalBreakHours;

    if (isOvernight != null && Boolean.TRUE.equals(isOvernight)) {

      totalBreakHours = 1440 - Duration.between(endBreakTime, initialBreakTime).toMinutes();

    } else {

      totalBreakHours = Duration.between(initialBreakTime, endBreakTime).toHours();
      
    }

    if(totalBreakHours < 0 || totalBreakHours > 160){

      throw new IllegalArgumentException("Total break time must be between 0 and 2,5 hours.");

    }

  }

  public void validateIsActiveWorkTime(Long id) {

    if (workTimeRepository.findByIdAndIsActive(id ,true).isPresent()) {

      throw new IllegalArgumentException("There is already an active work time.");

    }

  }

  public void validateRequireAndAutoPunchSameTime(Boolean requiresPunch, Boolean autoGeneratePunches) {

    if(Boolean.TRUE.equals(requiresPunch) && Boolean.TRUE.equals(autoGeneratePunches)) {

      throw new IllegalArgumentException("A work time cannot require punches and auto-generate punches at the same time.");

    }

  }

}
