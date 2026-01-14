package com.example.collab.service.validation;

import org.springframework.stereotype.Component;

import com.example.collab.repository.WorkScheduleRepository;

@Component
public class WorkScheduleValidator {

  WorkScheduleRepository workScheduleRepository;

  public WorkScheduleValidator(WorkScheduleRepository workScheduleRepository) {

    this.workScheduleRepository = workScheduleRepository;

  }

  public void validateManyDaysPerWeek(Integer workDaysPerWeek) {}

  public void validateManyRestDaysPerWeek(Integer restDaysPerWeek) {}

  public void validateManyDaysPerWeek(Integer workDaysPerWeek, Integer restDaysPerWeek) {}

  public void validateIsActiveWorkSchedule(Long id) {}

}
