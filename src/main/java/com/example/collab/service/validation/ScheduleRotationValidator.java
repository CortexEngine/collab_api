package com.example.collab.service.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.repository.*;
import com.example.collab.exception.business.InvalidScheduleRotationException;
import com.example.collab.exception.domain.DuplicatedScheduleRotationException;
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

    Long existingWorkDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, true);

    Long newWorkDaysCount = 0L;

    if (workdays != null) {

      newWorkDaysCount = workdays.stream().filter(workday -> Boolean.TRUE.equals(workday)).count();

    }

    Long totalWorkDays = existingWorkDays + newWorkDaysCount;

    if (totalWorkDays > 6) {

      throw new InvalidScheduleRotationException("A schedule cannot have more than 6 work days per week.");

    }

  }

  public void validateMinDayIndexPerSchedule(Long workScheduleId, List<Boolean> workdays) {

    Long existingRestDays = scheduleRotationRepository.countByworkScheduleIdAndWorkday(workScheduleId, false);

    Long newRestDaysCount = 0L;

    if (workdays != null) {

      newRestDaysCount = workdays.stream().filter(workday -> Boolean.FALSE.equals(workday)).count();

    }

    Long totalRestDays = existingRestDays + newRestDaysCount;

    Boolean hasInsufficientRestDays = totalRestDays < 1;

    Boolean hasExcessiveRestDays = totalRestDays > 3;

    if (hasInsufficientRestDays || hasExcessiveRestDays) {

      throw new InvalidScheduleRotationException("A schedule must have at least 1 rest day per week or no more than 3 rest days per week.");
      
    }

  }

  public void validateTotalDaysMatchSchedule(Long workScheduleId, List<Integer> dayIndexs) {

    WorkSchedule workSchedule = workScheduleRepository.findById(workScheduleId).orElseThrow(() -> new InvalidScheduleRotationException("Work schedule not found"));

    Integer expectedTotalDays = workSchedule.getWorkDaysPerWeek() + workSchedule.getRestDaysPerWeek();

    Long existingRotationsCount = scheduleRotationRepository.countByWorkScheduleId(workScheduleId);

    Long newRotationsCount = 0L;

    if (dayIndexs != null) {

      newRotationsCount = Long.valueOf(dayIndexs.size());

    }

    Long totalRotationsCount = existingRotationsCount + newRotationsCount;

    boolean daysMismatch = totalRotationsCount != expectedTotalDays.longValue();

    if (daysMismatch) {

      throw new InvalidScheduleRotationException("Schedule requires " + expectedTotalDays + " days but has " + totalRotationsCount + " rotations configured.");

    }

  }

  public void validateDuplicateDayIndex(Long workScheduleId, List<Integer> dayIndexs) {

    if (dayIndexs == null || dayIndexs.isEmpty()) {

      return;

    }

    Set<Integer> seen = new HashSet<>();

    for (Integer dayIndex : dayIndexs) {

      if (!seen.add(dayIndex)) {

        throw new InvalidScheduleRotationException("Day index " + dayIndex + " is duplicated in request.");

      }

      if (scheduleRotationRepository.findByWorkScheduleIdAndDayIndexs(workScheduleId, dayIndex).isPresent()) {

        throw new DuplicatedScheduleRotationException("Day index " + dayIndex + " already exists for this work schedule.");

      }

    }

  }

  public void validateDayIndexesAndWorkdaysSize(List<Integer> dayIndexs, List<Boolean> workdays) {

    if (dayIndexs == null || workdays == null) {

      return;

    }

    if (dayIndexs.size() != workdays.size()) {

      throw new InvalidScheduleRotationException("Day indexes and workdays must have the same size.");
      
    }

  }

}
