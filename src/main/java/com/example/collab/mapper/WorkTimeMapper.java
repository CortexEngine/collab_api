package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkTimeMapper {

  @Mapping(target = "id", ignore = true)
  WorkTime toEntity (WorkTimeRequestDTO dto);

  void updateEntity (@MappingTarget WorkTime workTime, WorkTime dto);

  WorkTimeResponseDTO toResponse(WorkTime workTime);

}
