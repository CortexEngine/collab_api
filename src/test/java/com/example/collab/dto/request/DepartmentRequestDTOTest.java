package com.example.collab.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("DepartmentRequestDTO Validation Tests")
class DepartmentRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should validate department request with all valid fields")
    void shouldValidateDepartmentRequestWithAllValidFields() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.of(2024, 1, 1));
        dto.setEndDate(LocalDate.of(2026, 12, 31));
        dto.setManagerRegistration(1001);
        dto.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        dto.setTeamMembersRegistration(Arrays.asList(2001, 2002));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when name is null")
    void shouldFailValidationWhenNameIsNull() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.now().minusDays(1));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    @DisplayName("Should fail validation when number is null")
    void shouldFailValidationWhenNumberIsNull() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setInitialDate(LocalDate.now().minusDays(1));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")));
    }

    @Test
    @DisplayName("Should fail validation when initial date is in the future")
    void shouldFailValidationWhenInitialDateIsInFuture() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.now().plusDays(1));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("initialDate") && 
            v.getConstraintDescriptor().getAnnotation() instanceof PastOrPresent
        ));
    }

    @Test
    @DisplayName("Should fail validation when end date is in the past")
    void shouldFailValidationWhenEndDateIsInPast() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.now().minusDays(1));
        dto.setEndDate(LocalDate.now().minusDays(1));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("endDate") && 
            v.getConstraintDescriptor().getAnnotation() instanceof Future
        ));
    }

    @Test
    @DisplayName("Should fail validation when manager registration is negative")
    void shouldFailValidationWhenManagerRegistrationIsNegative() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.now().minusDays(1));
        dto.setManagerRegistration(-1);

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("managerRegistration")
        ));
    }

    @Test
    @DisplayName("Should validate with null optional fields")
    void shouldValidateWithNullOptionalFields() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        // initialDate, endDate, managerRegistration, etc. are null

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert - Should only fail on required fields
        assertTrue(violations.isEmpty() || 
            violations.stream().noneMatch(v -> 
                v.getPropertyPath().toString().equals("initialDate") ||
                v.getPropertyPath().toString().equals("endDate")
            )
        );
    }

    @Test
    @DisplayName("Should set and get all fields correctly")
    void shouldSetAndGetAllFieldsCorrectly() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        String name = "HR Department";
        Integer number = 202;
        LocalDate initialDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        Integer managerRegistration = 1001;
        var supportManagers = Arrays.asList(1002, 1003);
        var teamMembers = Arrays.asList(2001, 2002, 2003);

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
    @DisplayName("Should handle date edge cases")
    void shouldHandleDateEdgeCases() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setInitialDate(LocalDate.now()); // Today is valid for PastOrPresent
        dto.setEndDate(LocalDate.now().plusDays(1)); // Tomorrow is valid for Future

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate lists of registrations")
    void shouldValidateListsOfRegistrations() {
        // Arrange
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Engineering");
        dto.setNumber(101);
        dto.setManagerRegistration(1001);
        dto.setManagerSupportRegistration(Arrays.asList(1002, 1003, 1004));
        dto.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003, 2004, 2005));

        // Act
        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
        assertEquals(3, dto.getManagerSupportRegistration().size());
        assertEquals(5, dto.getTeamMembersRegistration().size());
    }
}
