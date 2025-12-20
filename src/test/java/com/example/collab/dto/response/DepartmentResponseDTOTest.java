package com.example.collab.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DepartmentResponseDTO Tests")
class DepartmentResponseDTOTest {

    @Test
    @DisplayName("Should create response DTO with all fields")
    void shouldCreateResponseDTOWithAllFields() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        String name = "Engineering";
        Integer number = 101;
        LocalDate initialDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        Integer managerRegistration = 1001;
        List<Integer> supportManagers = Arrays.asList(1002, 1003);
        List<Integer> teamMembers = Arrays.asList(2001, 2002, 2003);

        // Act
        dto.setName(name);
        dto.setNumber(number);
        dto.setInitialDate(initialDate);
        dto.setEndDate(endDate);
        dto.setManagerRegistration(managerRegistration);
        dto.setManagerSupportRegistration(supportManagers);
        dto.setTeamMembersRegistration(teamMembers);

        // Assert
        assertEquals(name, dto.getName());
        assertEquals(number, dto.getNumber());
        assertEquals(initialDate, dto.getInitialDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(managerRegistration, dto.getManagerRegistration());
        assertEquals(supportManagers, dto.getManagerSupportRegistration());
        assertEquals(teamMembers, dto.getTeamMembersRegistration());
    }

    @Test
    @DisplayName("Should handle null values")
    void shouldHandleNullValues() {
        // Arrange & Act
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setName("Test");
        dto.setNumber(101);

        // Assert
        assertNotNull(dto);
        assertEquals("Test", dto.getName());
        assertNull(dto.getInitialDate());
        assertNull(dto.getEndDate());
        assertNull(dto.getManagerRegistration());
        assertNull(dto.getManagerSupportRegistration());
        assertNull(dto.getTeamMembersRegistration());
    }

    @Test
    @DisplayName("Should serialize dates correctly")
    void shouldSerializeDatesCorrectly() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        LocalDate initialDate = LocalDate.of(2024, 3, 15);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        // Act
        dto.setInitialDate(initialDate);
        dto.setEndDate(endDate);

        // Assert
        assertEquals(initialDate, dto.getInitialDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals("2024-03-15", dto.getInitialDate().toString());
        assertEquals("2024-12-31", dto.getEndDate().toString());
    }

    @Test
    @DisplayName("Should handle empty lists")
    void shouldHandleEmptyLists() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();

        // Act
        dto.setManagerSupportRegistration(Arrays.asList());
        dto.setTeamMembersRegistration(Arrays.asList());

        // Assert
        assertNotNull(dto.getManagerSupportRegistration());
        assertNotNull(dto.getTeamMembersRegistration());
        assertTrue(dto.getManagerSupportRegistration().isEmpty());
        assertTrue(dto.getTeamMembersRegistration().isEmpty());
    }

    @Test
    @DisplayName("Should handle large lists of registrations")
    void shouldHandleLargeListsOfRegistrations() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        List<Integer> largeList = Arrays.asList(
            1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010
        );

        // Act
        dto.setTeamMembersRegistration(largeList);

        // Assert
        assertEquals(10, dto.getTeamMembersRegistration().size());
        assertTrue(dto.getTeamMembersRegistration().contains(1001));
        assertTrue(dto.getTeamMembersRegistration().contains(1010));
    }

    @Test
    @DisplayName("Should be mutable after creation")
    void shouldBeMutableAfterCreation() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setName("Initial Name");
        dto.setNumber(100);

        // Act
        dto.setName("Updated Name");
        dto.setNumber(200);

        // Assert
        assertEquals("Updated Name", dto.getName());
        assertEquals(200, dto.getNumber());
    }

    @Test
    @DisplayName("Should maintain data integrity")
    void shouldMaintainDataIntegrity() {
        // Arrange
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        Integer originalNumber = 101;
        String originalName = "Engineering";

        // Act
        dto.setNumber(originalNumber);
        dto.setName(originalName);

        // Assert
        assertSame(originalNumber, dto.getNumber());
        assertSame(originalName, dto.getName());
    }

    @Test
    @DisplayName("Should support chaining with Lombok Data")
    void shouldSupportChainingWithLombokData() {
        // Arrange & Act
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setManagerRegistration(1001);

        // Assert - Verify all setters worked
        assertNotNull(dto);
        assertEquals("Engineering", dto.getName());
        assertEquals(101, dto.getNumber());
        assertEquals(1001, dto.getManagerRegistration());
    }
}
