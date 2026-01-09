package com.example.collab.mapper;

import com.example.collab.domain.model.ScheduleRotation;

public class ScheduleRotationMapper {

  ScheduleRotation toEntity (ScheduleRotationRequestDTO dto);

  void updateEntity (@MappingTarget ScheduleRotation scheduleRotation, ScheduleRotation dto);

  ScheduleRotationResponseDTO toResponse(ScheduleRotation scheduleRotation);

}
