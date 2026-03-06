package com.example.collab.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;

@DisplayName("DepartmentMapper Tests")
class DepartmentMapperTest {

    private final DepartmentMapper departmentMapper = Mappers.getMapper(DepartmentMapper.class);

    @Test
    @DisplayName("Should map request dto to entity")
    void shouldMapRequestDtoToEntity() {
        DepartmentRequestDTO request = new DepartmentRequestDTO(
            "Engineering",
            101,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2025, 12, 31),
            1001,
            List.of(1002, 1003),
            List.of(2001, 2002, 2003)
        );

        Department result = departmentMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        assertEquals(101, result.getNumber());
        assertEquals(2, result.getManagerSupportRegistration().size());
    }

    @Test
    @DisplayName("Should map entity to response dto")
    void shouldMapEntityToResponseDto() {
        Department department = new Department();
        department.setName("Engineering");
        department.setNumber(101);
        department.setInitialDate(LocalDate.of(2024, 1, 1));

        DepartmentResponseDTO result = departmentMapper.toResponse(department);

        assertNotNull(result);
        assertEquals("Engineering", result.name());
        assertEquals(101, result.number());
        assertEquals(LocalDate.of(2024, 1, 1), result.initialDate());
    }
}
