package com.example.collab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.WorkSchedule;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

  Optional<WorkSchedule> findByDescription(String description);

  Optional<WorkSchedule> findByIsActive(Boolean isActive);

  Optional<WorkSchedule> findByWorkDaysPerWeekAndRestDaysPerWeek(Integer workDays, Integer restDays);

}
