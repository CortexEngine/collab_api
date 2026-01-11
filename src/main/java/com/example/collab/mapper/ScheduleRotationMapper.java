package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.ScheduleRotation;
import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScheduleRotationMapper {

  @Mapping(target = "workSchedule", source = "workSchedule", qualifiedByName = "toWorkSchedule")
  @Mapping(target = "workTime", source = "workTime", qualifiedByName = "toWorkTime")
  @Mapping(target = "dayIndex", source = "dayIndex")
  @Mapping(target = "workday", source = "workday")
  ScheduleRotation toEntity (ScheduleRotationRequestDTO dto);

  @Mapping(target = "workSchedule", source = "workSchedule")
  @Mapping(target = "workTime", source = "workTime")
  @Mapping(target = "dayIndex", source = "dayIndex")
  @Mapping(target = "workday", source = "workday")
  void updateEntity (@MappingTarget ScheduleRotation scheduleRotation, ScheduleRotation dto);
  
  @Mapping(target = "workSchedule", source = "workSchedule", qualifiedByName = "fromWorkSchedule")
  @Mapping(target = "workTime", source = "workTime", qualifiedByName = "fromWorkTime")
  @Mapping(target = "dayIndex", source = "dayIndex")
  @Mapping(target = "workday", source = "workday")
  ScheduleRotationResponseDTO toResponse(ScheduleRotation scheduleRotation);

  @Named("toWorkSchedule")
  default WorkSchedule toWorkSchedule (Long value){

    return value != null ? new WorkSchedule(value) : null;

  }

  @Named("fromWorkSchedule")
  default Long fromWorkSchedule (WorkSchedule workSchedule){

    return workSchedule != null ? workSchedule.getId() : null;

  }

  @Named("toWorkTime")
  default WorkTime toWorkTime (Long value){

    return value != null ? new WorkTime(value) : null;

  }

  @Named("fromWorkTime")
  default Long fromWorkTime (WorkTime workTime){

    return workTime != null ? workTime.getId() : null;

  }


}
