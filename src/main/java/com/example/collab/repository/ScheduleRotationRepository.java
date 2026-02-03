package com.example.collab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.ScheduleRotation;

public interface ScheduleRotationRepository extends JpaRepository<ScheduleRotation, Long> {

  Optional<ScheduleRotation> findByWorkScheduleId(Long workScheduleId);

  Optional<ScheduleRotation> findByWorkScheduleIdAndWorkTimeId(Long workScheduleId, Long workTimeId);

  Optional<ScheduleRotation> findByWorkScheduleIdAndDayIndexs(Long workScheduleId, Integer dayIndexs);

  Long countByworkScheduleIdAndWorkday(Long workScheduleId, Boolean workday);

  Long countByWorkScheduleId(Long workScheduleId);

}
