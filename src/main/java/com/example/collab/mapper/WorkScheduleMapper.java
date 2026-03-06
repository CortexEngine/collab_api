package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkScheduleMapper {

  @Mapping(target = "id", ignore = true)
  WorkSchedule toEntity (WorkScheduleRequestDTO dto);

  void updateEntity (@MappingTarget WorkSchedule workSchedule, WorkSchedule dto);

  WorkScheduleResponseDTO toResponse(WorkSchedule workSchedule);
  
}
