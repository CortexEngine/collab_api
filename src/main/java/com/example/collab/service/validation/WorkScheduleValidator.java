package com.example.collab.service.validation;

import org.springframework.stereotype.Component;

import com.example.collab.repository.WorkScheduleRepository;
import com.example.collab.exception.business.InvalidWorkScheduleException;
import com.example.collab.exception.domain.DuplicatedWorkScheduleException;

@Component
public class WorkScheduleValidator {

  WorkScheduleRepository workScheduleRepository;

  public WorkScheduleValidator(WorkScheduleRepository workScheduleRepository) {

    this.workScheduleRepository = workScheduleRepository;

  }

  public void validateManyWorkDaysPerWeek(Integer workDaysPerWeek) {

    if (workDaysPerWeek < 1 || workDaysPerWeek > 7) {

      throw new InvalidWorkScheduleException("Work days per week must be between 1 and 7.");

    }

  }

  public void validateManyRestDaysPerWeek(Integer restDaysPerWeek) {

    if (restDaysPerWeek < 1 || restDaysPerWeek > 7) {

      throw new InvalidWorkScheduleException("Rest days per week must be between 1 and 7.");

    }

  }

  public void validateManyDaysPerWeek(Integer workDaysPerWeek, Integer restDaysPerWeek) {

    if (workDaysPerWeek + restDaysPerWeek > 7) {

      throw new InvalidWorkScheduleException("The sum of work days and rest days per week cannot exceed 7.");

    }

  }

  public void validateIsActiveWorkSchedule(Long id) {

    if (workScheduleRepository.findByIdAndIsActive(id, true).isPresent()) {

      throw new DuplicatedWorkScheduleException("There is already an active work schedule.");
      
    }

  }

  public void validateDescriptionExists(String description) {

    if (workScheduleRepository.findByDescription(description).isPresent()) {

      throw new DuplicatedWorkScheduleException("A work schedule with the same description already exists.");

    }

  }

}
