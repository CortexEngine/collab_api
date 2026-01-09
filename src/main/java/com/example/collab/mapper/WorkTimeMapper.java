package com.example.collab.mapper;

import com.example.collab.domain.model.WorkTime;

public class WorkTimeMapper {

  WorkTime toEntity (WorkTimeRequestDTO dto);

  void updateEntity (@MappingTarget WorkTime workTime, WorkTime dto);

  WorkTimeResponseDTO toResponse(WorkTime workTime);

}
