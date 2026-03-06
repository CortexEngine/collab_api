package com.example.collab.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("WorkScheduleRequestDTO Tests")
class WorkScheduleRequestDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Should create valid WorkScheduleRequestDTO")
    void shouldCreateValidWorkScheduleRequestDTO() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        assertNotNull(dto);
        assertEquals("Scale 5x2", dto.description());
        assertEquals(5, dto.workDaysPerWeek());
        assertEquals(2, dto.restDaysPerWeek());
        assertTrue(dto.isActive());
    }

    @Test
    @DisplayName("Should validate with valid work days per week")
    void shouldValidateWithValidWorkDaysPerWeek() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate with valid rest days per week")
    void shouldValidateWithValidRestDaysPerWeek() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null description")
    void shouldFailValidationWithNullDescription() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            null,
            5,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    @DisplayName("Should fail validation with blank description")
    void shouldFailValidationWithBlankDescription() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "   ",
            5,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    @DisplayName("Should fail validation with empty description")
    void shouldFailValidationWithEmptyDescription() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "",
            5,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null workDaysPerWeek")
    void shouldFailValidationWithNullWorkDaysPerWeek() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            null,
            2,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("workDaysPerWeek")));
    }

    @Test
    @DisplayName("Should fail validation with null restDaysPerWeek")
    void shouldFailValidationWithNullRestDaysPerWeek() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            null,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("restDaysPerWeek")));
    }

    @Test
    @DisplayName("Should fail validation with null isActive")
    void shouldFailValidationWithNullIsActive() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            null
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isActive")));
    }

    @Test
    @DisplayName("Should fail validation with workDaysPerWeek below minimum")
    void shouldFailValidationWithWorkDaysPerWeekBelowMinimum() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 0x7",
            0,
            7,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with workDaysPerWeek above maximum")
    void shouldFailValidationWithWorkDaysPerWeekAboveMaximum() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 8x0",
            8,
            0,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with restDaysPerWeek below minimum")
    void shouldFailValidationWithRestDaysPerWeekBelowMinimum() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 7x0",
            7,
            0,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with restDaysPerWeek above maximum")
    void shouldFailValidationWithRestDaysPerWeekAboveMaximum() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 0x8",
            0,
            8,
            true
        );

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should test equality between two identical DTOs")
    void shouldTestEqualityBetweenIdenticalDTOs() {
        WorkScheduleRequestDTO dto1 = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        WorkScheduleRequestDTO dto2 = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different DTOs")
    void shouldTestInequalityBetweenDifferentDTOs() {
        WorkScheduleRequestDTO dto1 = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        WorkScheduleRequestDTO dto2 = new WorkScheduleRequestDTO(
            "Scale 6x1",
            6,
            1,
            true
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation")
    void shouldTestStringRepresentation() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("WorkScheduleRequestDTO") || str.contains("Scale 5x2"));
    }

    @Test
    @DisplayName("Should handle inactive status")
    void shouldHandleInactiveStatus() {
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            false
        );

        assertNotNull(dto);
        assertEquals("Scale 5x2", dto.description());
        assertFalse(dto.isActive());

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should handle long description")
    void shouldHandleLongDescription() {
        String longDescription = "Scale 5 days work and 2 days rest with flexible hours";
        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            longDescription,
            5,
            2,
            true
        );

        assertNotNull(dto);
        assertEquals(longDescription, dto.description());

        Set<ConstraintViolation<WorkScheduleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should test record field access")
    void shouldTestRecordFieldAccess() {
        String description = "Test Scale";
        Integer workDaysPerWeek = 5;
        Integer restDaysPerWeek = 2;
        Boolean isActive = true;

        WorkScheduleRequestDTO dto = new WorkScheduleRequestDTO(
            description,
            workDaysPerWeek,
            restDaysPerWeek,
            isActive
        );

        assertEquals(description, dto.description());
        assertEquals(workDaysPerWeek, dto.workDaysPerWeek());
        assertEquals(restDaysPerWeek, dto.restDaysPerWeek());
        assertEquals(isActive, dto.isActive());
    }
}
