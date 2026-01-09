package com.example.collab.mapper;

import com.example.collab.domain.model.WorkSchedule;

public class WorkScheduleMapper {

  WorkSchedule toEntity (WorkScheduleRequestDTO dto);

  void updateEntity (@MappingTarget WorkSchedule workSchedule, WorkSchedule dto);

  WorkScheduleResponseDTO toResponse(WorkSchedule workSchedule);
  
}
