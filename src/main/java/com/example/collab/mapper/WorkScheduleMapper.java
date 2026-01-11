package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkScheduleMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "description", source = "description")
  @Mapping(target = "workDaysPerWeek", source = "workDaysPerWeek")
  @Mapping(target = "restDaysPerWeek", source = "restDaysPerWeek")
  @Mapping(target = "isActive", source = "isActive")
  WorkSchedule toEntity (WorkScheduleRequestDTO dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "description", source = "description")
  @Mapping(target = "workDaysPerWeek", source = "workDaysPerWeek")
  @Mapping(target = "restDaysPerWeek", source = "restDaysPerWeek")
  @Mapping(target = "isActive", source = "isActive")
  void updateEntity (@MappingTarget WorkSchedule workSchedule, WorkSchedule dto);

  @Mapping(target = "description", source = "description")
  @Mapping(target = "workDaysPerWeek", source = "workDaysPerWeek")
  @Mapping(target = "restDaysPerWeek", source = "restDaysPerWeek")
  @Mapping(target = "isActive", source = "isActive")
  WorkScheduleResponseDTO toResponse(WorkSchedule workSchedule);
  
}
