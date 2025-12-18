package com.example.collab.mapper;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DepartmentMapper Tests")
class DepartmentMapperTest {

    private DepartmentMapper departmentMapper;

    private DepartmentRequestDTO departmentRequestDTO;
    private Department department;

    @BeforeEach
    void setUp() {
        departmentMapper = Mappers.getMapper(DepartmentMapper.class);

        // Setup request DTO
        departmentRequestDTO = new DepartmentRequestDTO();
        departmentRequestDTO.setName("Engineering");
        departmentRequestDTO.setNumber(101);
        departmentRequestDTO.setInitialDate(LocalDate.of(2024, 1, 1));
        departmentRequestDTO.setEndDate(LocalDate.of(2025, 12, 31));
        departmentRequestDTO.setManagerRegistration(1001);
        departmentRequestDTO.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        departmentRequestDTO.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

        // Setup entity
        department = new Department();
        department.setName("Engineering");
        department.setNumber(101);
        department.setInitialDate(LocalDate.of(2024, 1, 1));
        department.setEndDate(LocalDate.of(2025, 12, 31));
        department.setManagerRegistration(1001);
        department.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        department.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));
    }

    @Test
    @DisplayName("Should map DepartmentRequestDTO to Department entity")
    void shouldMapRequestDTOToEntity() {
        // Act
        Department result = departmentMapper.toEntity(departmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        assertEquals(101, result.getNumber());
        assertEquals(LocalDate.of(2024, 1, 1), result.getInitialDate());
        assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
        assertEquals(1001, result.getManagerRegistration());
        assertEquals(2, result.getManagerSupportRegistration().size());
        assertEquals(3, result.getTeamMembersRegistration().size());
        assertTrue(result.getManagerSupportRegistration().contains(1002));
        assertTrue(result.getTeamMembersRegistration().contains(2001));
    }

    @Test
    @DisplayName("Should map Department entity to DepartmentResponseDTO")
    void shouldMapEntityToResponseDTO() {
        // Act
        DepartmentResponseDTO result = departmentMapper.toResponse(department);

        // Assert
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        assertEquals(101, result.getNumber());
        assertEquals(LocalDate.of(2024, 1, 1), result.getInitialDate());
        assertEquals(LocalDate.of(2025, 12, 31), result.getEndDate());
        assertEquals(1001, result.getManagerRegistration());
        assertEquals(2, result.getManagerSupportRegistration().size());
        assertEquals(3, result.getTeamMembersRegistration().size());
    }

    @Test
    @DisplayName("Should update existing Department entity from DepartmentRequestDTO")
    void shouldUpdateEntityFromRequestDTO() {
        // Arrange
        Department existingDepartment = new Department();
        existingDepartment.setName("Old Name");
        existingDepartment.setNumber(999);

        DepartmentRequestDTO updateDTO = new DepartmentRequestDTO();
        updateDTO.setName("Updated Engineering");
        updateDTO.setNumber(102);
        updateDTO.setInitialDate(LocalDate.of(2024, 6, 1));
        updateDTO.setEndDate(LocalDate.of(2026, 6, 30));
        updateDTO.setManagerRegistration(2001);
        updateDTO.setManagerSupportRegistration(Arrays.asList(2002));
        updateDTO.setTeamMembersRegistration(Arrays.asList(3001, 3002));

        // Act
        departmentMapper.updateEntity(existingDepartment, updateDTO);

        // Assert
        assertNotNull(existingDepartment);
    //  assertEquals(1L, existingDepartment.getId()); // ID should not change
        assertEquals("Updated Engineering", existingDepartment.getName());
        assertEquals(102, existingDepartment.getNumber());
        assertEquals(LocalDate.of(2024, 6, 1), existingDepartment.getInitialDate());
        assertEquals(LocalDate.of(2026, 6, 30), existingDepartment.getEndDate());
        assertEquals(2001, existingDepartment.getManagerRegistration());
        assertEquals(1, existingDepartment.getManagerSupportRegistration().size());
        assertEquals(2, existingDepartment.getTeamMembersRegistration().size());
    }

    @Test
    @DisplayName("Should handle null values in DepartmentRequestDTO")
    void shouldHandleNullValuesInRequestDTO() {
        // Arrange
        DepartmentRequestDTO nullDTO = new DepartmentRequestDTO();
        nullDTO.setName("Test Department");
        nullDTO.setNumber(200);
        // Other fields are null

        // Act
        Department result = departmentMapper.toEntity(nullDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Department", result.getName());
        assertEquals(200, result.getNumber());
        assertNull(result.getInitialDate());
        assertNull(result.getEndDate());
        assertNull(result.getManagerRegistration());
    }

    @Test
    @DisplayName("Should handle null Department entity")
    void shouldHandleNullEntity() {
        // Act
        DepartmentResponseDTO result = departmentMapper.toResponse(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null DepartmentRequestDTO")
    void shouldHandleNullRequestDTO() {
        // Act
        Department result = departmentMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should map empty lists correctly")
    void shouldMapEmptyListsCorrectly() {
        // Arrange
        DepartmentRequestDTO emptyListDTO = new DepartmentRequestDTO();
        emptyListDTO.setName("Empty Lists Department");
        emptyListDTO.setNumber(300);
        emptyListDTO.setManagerSupportRegistration(Arrays.asList());
        emptyListDTO.setTeamMembersRegistration(Arrays.asList());

        // Act
        Department result = departmentMapper.toEntity(emptyListDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Empty Lists Department", result.getName());
        assertTrue(result.getManagerSupportRegistration().isEmpty());
        assertTrue(result.getTeamMembersRegistration().isEmpty());
    }

    @Test
    @DisplayName("Should preserve list order when mapping")
    void shouldPreserveListOrderWhenMapping() {
        // Arrange
        List<Integer> orderedSupport = Arrays.asList(5001, 5002, 5003);
        List<Integer> orderedMembers = Arrays.asList(6001, 6002, 6003, 6004);
        
        departmentRequestDTO.setManagerSupportRegistration(orderedSupport);
        departmentRequestDTO.setTeamMembersRegistration(orderedMembers);

        // Act
        Department result = departmentMapper.toEntity(departmentRequestDTO);

        // Assert
        assertEquals(5001, result.getManagerSupportRegistration().get(0));
        assertEquals(5002, result.getManagerSupportRegistration().get(1));
        assertEquals(5003, result.getManagerSupportRegistration().get(2));
        assertEquals(6001, result.getTeamMembersRegistration().get(0));
        assertEquals(6004, result.getTeamMembersRegistration().get(3));
    }

    @Test
    @DisplayName("Should map dates correctly")
    void shouldMapDatesCorrectly() {
        // Arrange
        LocalDate specificInitialDate = LocalDate.of(2023, 3, 15);
        LocalDate specificEndDate = LocalDate.of(2024, 9, 30);
        
        departmentRequestDTO.setInitialDate(specificInitialDate);
        departmentRequestDTO.setEndDate(specificEndDate);

        // Act
        Department result = departmentMapper.toEntity(departmentRequestDTO);

        // Assert
        assertEquals(specificInitialDate, result.getInitialDate());
        assertEquals(specificEndDate, result.getEndDate());
    }

  /*   @Test
    @DisplayName("Should not modify ID when updating entity")
    void shouldNotModifyIdWhenUpdatingEntity() {
        // Arrange
        Long originalId = 999L;
        Department existingDepartment = new Department();
        existingDepartment.setName("Original Name");

        // Act
        departmentMapper.updateEntity(existingDepartment, departmentRequestDTO);

        // Assert
        assertEquals(originalId, existingDepartment.getId());
    } */
}
