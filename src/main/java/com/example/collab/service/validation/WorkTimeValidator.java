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

  public void validateTimeEndBeforeIntialTime(LocalTime initialTime, LocalTime endTime){}

  public void validateSameWorkTimes(LocalTime initialTime, LocalTime endTime, LocalTime initialBreakTime, LocalTime endBreakTime) {}

  public void validateInitialandEndWorkTimeLimit (LocalTime initialTime, LocalTime endTime){}

  public void validateInitialAndEndBreakTimeLimit(LocalTime initialBreakTime, LocalTime endBreakTime) {}

  public void validadeIsActiveWorkTime(Long id) {}

}
