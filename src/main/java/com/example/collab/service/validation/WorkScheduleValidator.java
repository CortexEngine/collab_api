package com.example.collab.service.validation;

import org.springframework.stereotype.Component;

import com.example.collab.repository.WorkScheduleRepository;

@Component
public class WorkScheduleValidator {

  WorkScheduleRepository workScheduleRepository;

  public WorkScheduleValidator(WorkScheduleRepository workScheduleRepository) {

    this.workScheduleRepository = workScheduleRepository;

  }

  public void validateManyDaysPerWeek(Integer workDaysPerWeek) {

    if (workDaysPerWeek < 1 || workDaysPerWeek > 7) {

      throw new IllegalArgumentException("Work days per week must be between 1 and 7.");

    }

  }

  public void validateManyRestDaysPerWeek(Integer restDaysPerWeek) {

    if (restDaysPerWeek < 1 || restDaysPerWeek > 7) {

      throw new IllegalArgumentException("Rest days per week must be between 1 and 7.");

    }

  }

  public void validateManyDaysPerWeek(Integer workDaysPerWeek, Integer restDaysPerWeek) {

    if (workDaysPerWeek + restDaysPerWeek > 7) {

      throw new IllegalArgumentException("The sum of work days and rest days per week cannot exceed 7.");

    }

  }

  public void validateIsActiveWorkSchedule(Long id) {

    if (workScheduleRepository.findByIdAndIsActive(id, true).isEmpty()) {

      throw new IllegalArgumentException("The work schedule is not active or does not exist.");
      
    }

  }

}
