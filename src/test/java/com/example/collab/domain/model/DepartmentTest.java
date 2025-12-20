package com.example.collab.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Department Entity Tests")
class DepartmentTest {

    @Test
    @DisplayName("Should create Department with all fields")
    void shouldCreateDepartmentWithAllFields() {
        // Arrange
        String name = "Engineering";
        Integer number = 101;
        LocalDate initialDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        Integer managerRegistration = 1001;
        List<Integer> supportManagers = Arrays.asList(1002, 1003);
        List<Integer> teamMembers = Arrays.asList(2001, 2002, 2003);

        // Act
        Department department = new Department();
        department.setName(name);
        department.setNumber(number);
        department.setInitialDate(initialDate);
        department.setEndDate(endDate);
        department.setManagerRegistration(managerRegistration);
        department.setManagerSupportRegistration(supportManagers);
        department.setTeamMembersRegistration(teamMembers);

        // Assert
        assertNotNull(department);
        assertEquals(name, department.getName());
        assertEquals(number, department.getNumber());
        assertEquals(initialDate, department.getInitialDate());
        assertEquals(endDate, department.getEndDate());
        assertEquals(managerRegistration, department.getManagerRegistration());
        assertEquals(supportManagers, department.getManagerSupportRegistration());
        assertEquals(teamMembers, department.getTeamMembersRegistration());
    }

    @Test
    @DisplayName("Should create Department using AllArgsConstructor")
    void shouldCreateDepartmentUsingAllArgsConstructor() {
        // Arrange & Act
        Department department = new Department(
            1L,
            "HR Department",
            102,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2025, 12, 31),
            1001,
            Arrays.asList(1002, 1003),
            Arrays.asList(2001, 2002)
        );

        // Assert
        assertNotNull(department);
        assertEquals(1L, department.getId());
        assertEquals("HR Department", department.getName());
        assertEquals(102, department.getNumber());
        assertEquals(1001, department.getManagerRegistration());
        assertEquals(2, department.getManagerSupportRegistration().size());
        assertEquals(2, department.getTeamMembersRegistration().size());
    }

    @Test
    @DisplayName("Should create empty Department using NoArgsConstructor")
    void shouldCreateEmptyDepartmentUsingNoArgsConstructor() {
        // Act
        Department department = new Department();

        // Assert
        assertNotNull(department);
        assertNull(department.getId());
        assertNull(department.getName());
        assertNull(department.getNumber());
    }

    @Test
    @DisplayName("Should update Department fields")
    void shouldUpdateDepartmentFields() {
        // Arrange
        Department department = new Department();
        department.setName("IT");
        department.setNumber(200);

        // Act
        department.setName("Information Technology");
        department.setNumber(201);

        // Assert
        assertEquals("Information Technology", department.getName());
        assertEquals(201, department.getNumber());
    }

    @Test
    @DisplayName("Should handle null values in optional fields")
    void shouldHandleNullValuesInOptionalFields() {
        // Arrange & Act
        Department department = new Department();
        department.setName("Sales");
        department.setNumber(300);
        department.setEndDate(null);
        department.setManagerSupportRegistration(null);

        // Assert
        assertNotNull(department);
        assertEquals("Sales", department.getName());
        assertNull(department.getEndDate());
        assertNull(department.getManagerSupportRegistration());
    }

    @Test
    @DisplayName("Should add and modify support managers list")
    void shouldAddAndModifySupportManagersList() {
        // Arrange
        Department department = new Department();
        List<Integer> supportManagers = Arrays.asList(1001, 1002);
        department.setManagerSupportRegistration(supportManagers);

        // Act
        List<Integer> newSupportManagers = Arrays.asList(1001, 1002, 1003);
        department.setManagerSupportRegistration(newSupportManagers);

        // Assert
        assertEquals(3, department.getManagerSupportRegistration().size());
        assertTrue(department.getManagerSupportRegistration().contains(1003));
    }

    @Test
    @DisplayName("Should add and modify team members list")
    void shouldAddAndModifyTeamMembersList() {
        // Arrange
        Department department = new Department();
        List<Integer> teamMembers = Arrays.asList(2001, 2002);
        department.setTeamMembersRegistration(teamMembers);

        // Act
        List<Integer> newTeamMembers = Arrays.asList(2001, 2002, 2003, 2004);
        department.setTeamMembersRegistration(newTeamMembers);

        // Assert
        assertEquals(4, department.getTeamMembersRegistration().size());
        assertTrue(department.getTeamMembersRegistration().contains(2003) && department.getTeamMembersRegistration().contains(2004));
    }

    @Test
    @DisplayName("Should maintain unique department number")
    void shouldMaintainUniqueDepartmentNumber() {
        // Arrange
        Integer uniqueNumber = 999;
        Department department = new Department();
        
        // Act
        department.setNumber(uniqueNumber);

        // Assert
        assertEquals(uniqueNumber, department.getNumber());
    }

    @Test
    @DisplayName("Should set and get manager registration")
    void shouldSetAndGetManagerRegistration() {
        // Arrange
        Department department = new Department();
        Integer managerRegistration = 5001;

        // Act
        department.setManagerRegistration(managerRegistration);

        // Assert
        assertEquals(managerRegistration, department.getManagerRegistration());
    }

    @Test
    @DisplayName("Should handle date range correctly")
    void shouldHandleDateRangeCorrectly() {
        // Arrange
        Department department = new Department();
        LocalDate initialDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        // Act
        department.setInitialDate(initialDate);
        department.setEndDate(endDate);
 
        // Assert
        assertTrue(department.getInitialDate().isBefore(department.getEndDate()));
    }
}
