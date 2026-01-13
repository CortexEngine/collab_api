package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkTimeMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "description", source = "description")
  @Mapping(target = "isActive", source = "isActive")
  @Mapping(target = "initialTime", source = "initialTime")
  @Mapping(target = "endTime", source = "endTime")
  @Mapping(target = "initialBreakTime", source = "initialBreakTime")
  @Mapping(target = "endBreakTime", source = "endBreakTime")
  @Mapping(target = "requiresPunch", source = "requiresPunch")
  @Mapping(target = "autoGeneratePunches", source = "autoGeneratePunches")
  WorkTime toEntity (WorkTimeRequestDTO dto);

  @Mapping(target = "description", source = "description")
  @Mapping(target = "isActive", source = "isActive")
  @Mapping(target = "initialTime", source = "initialTime")
  @Mapping(target = "endTime", source = "endTime")
  @Mapping(target = "initialBreakTime", source = "initialBreakTime")
  @Mapping(target = "endBreakTime", source = "endBreakTime")
  @Mapping(target = "requiresPunch", source = "requiresPunch")
  @Mapping(target = "autoGeneratePunches", source = "autoGeneratePunches")
  void updateEntity (@MappingTarget WorkTime workTime, WorkTime dto);

  @Mapping(target = "description", source = "description")
  @Mapping(target = "isActive", source = "isActive")
  @Mapping(target = "initialTime", source = "initialTime")
  @Mapping(target = "endTime", source = "endTime")
  @Mapping(target = "initialBreakTime", source = "initialBreakTime")
  @Mapping(target = "endBreakTime", source = "endBreakTime")
  @Mapping(target = "requiresPunch", source = "requiresPunch")
  @Mapping(target = "autoGeneratePunches", source = "autoGeneratePunches")
  WorkTimeResponseDTO toResponse(WorkTime workTime);

}
