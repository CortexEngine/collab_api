package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "initialDate", source = "initialDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "managerRegistration", source = "managerRegistration")
    @Mapping(target = "managerSupportRegistration", source = "managerSupportRegistration")
    @Mapping(target = "teamMembersRegistration", source = "teamMembersRegistration")
    Department toEntity(DepartmentRequestDTO dto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "initialDate", source = "initialDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "managerRegistration", source = "managerRegistration")
    @Mapping(target = "managerSupportRegistration", source = "managerSupportRegistration")
    @Mapping(target = "teamMembersRegistration", source = "teamMembersRegistration")
    void updateEntity(@MappingTarget Department department, DepartmentRequestDTO dto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "initialDate", source = "initialDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "managerRegistration", source = "managerRegistration")
    @Mapping(target = "managerSupportRegistration", source = "managerSupportRegistration")
    @Mapping(target = "teamMembersRegistration", source = "teamMembersRegistration")
    DepartmentResponseDTO toResponse(Department department);

}
