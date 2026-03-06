package com.example.collab.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

  Optional<WorkTime> findByInitialTimeAndEndTime(LocalTime initialTime, LocalTime endTime);

  Optional<WorkTime> findByInitialTime(LocalTime intialTime);

  Optional<WorkTime> findByEndTime(LocalTime endTime);

  Optional<WorkTime> findByInitialBreakTimeAndEndBreakTime(LocalTime initialBreakTime, LocalTime endBreakTime);

  Optional<WorkTime> findByInitialBreakTime(LocalTime initialBreakTime);

  Optional<WorkTime> findByEndBreakTime(LocalTime endBreakTime);

  Optional<WorkTime> findByIdAndIsActive(Long id,Boolean isActive);

  Optional<WorkTime> findByDescription(String description);

  List<WorkTime> findByIsActive(boolean isActive);

}
