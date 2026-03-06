package com.example.collab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.collab.domain.model.ScheduleRotation;

public interface ScheduleRotationRepository extends JpaRepository<ScheduleRotation, Long> {

  Optional<ScheduleRotation> findByWorkScheduleId(Long workScheduleId);

  Optional<ScheduleRotation> findByWorkScheduleIdAndWorkTimeId(Long workScheduleId, Long workTimeId);

  @Query("SELECT sr FROM ScheduleRotation sr WHERE sr.workSchedule.id = :workScheduleId AND :dayIndex MEMBER OF sr.dayIndexs")
  Optional<ScheduleRotation> findByWorkScheduleIdAndDayIndexs(@Param("workScheduleId") Long workScheduleId, @Param("dayIndex") Integer dayIndex);

  @Query("SELECT COUNT(w) FROM ScheduleRotation sr JOIN sr.workdays w WHERE sr.workSchedule.id = :workScheduleId AND w = :workday")
  Long countByworkScheduleIdAndWorkday(@Param("workScheduleId") Long workScheduleId, @Param("workday") Boolean workday);

  Long countByWorkScheduleId(Long workScheduleId);

}
