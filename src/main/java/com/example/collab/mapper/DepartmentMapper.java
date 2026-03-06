package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    Department toEntity(DepartmentRequestDTO dto);

    void updateEntity(@MappingTarget Department department, DepartmentRequestDTO dto);

    DepartmentResponseDTO toResponse(Department department);

}
