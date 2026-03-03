package com.example.collab.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("WorkTimeRequestDTO Tests")
class WorkTimeRequestDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Should create valid WorkTimeRequestDTO")
    void shouldCreateValidWorkTimeRequestDTO() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertEquals("Commercial", dto.description());
        assertTrue(dto.isActive());
        assertEquals(LocalTime.of(8, 0), dto.initialTime());
        assertEquals(LocalTime.of(17, 0), dto.endTime());
        assertFalse(dto.isOvernight());
        assertEquals(LocalTime.of(12, 0), dto.initialBreakTime());
        assertEquals(LocalTime.of(13, 0), dto.endBreakTime());
        assertTrue(dto.requiresPunch());
        assertFalse(dto.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should validate with valid overnight shift")
    void shouldValidateWithValidOvernightShift() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Night Shift",
            true,
            LocalTime.of(22, 0),
            LocalTime.of(6, 0),
            true,
            LocalTime.of(2, 0),
            LocalTime.of(3, 0),
            false,
            true
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null description")
    void shouldFailValidationWithNullDescription() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            null,
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    @DisplayName("Should fail validation with blank description")
    void shouldFailValidationWithBlankDescription() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "   ",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null isActive")
    void shouldFailValidationWithNullIsActive() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            null,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null initialTime")
    void shouldFailValidationWithNullInitialTime() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            null,
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null endTime")
    void shouldFailValidationWithNullEndTime() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            null,
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null isOvernight")
    void shouldFailValidationWithNullIsOvernight() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            null,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null initialBreakTime")
    void shouldFailValidationWithNullInitialBreakTime() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            null,
            LocalTime.of(13, 0),
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null endBreakTime")
    void shouldFailValidationWithNullEndBreakTime() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            null,
            true,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null requiresPunch")
    void shouldFailValidationWithNullRequiresPunch() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            null,
            false
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null autoGeneratePunches")
    void shouldFailValidationWithNullAutoGeneratePunches() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            null
        );

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should test equality between two identical DTOs")
    void shouldTestEqualityBetweenIdenticalDTOs() {
        LocalTime initialTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        LocalTime breakStart = LocalTime.of(12, 0);
        LocalTime breakEnd = LocalTime.of(13, 0);

        WorkTimeRequestDTO dto1 = new WorkTimeRequestDTO(
            "Commercial",
            true,
            initialTime,
            endTime,
            false,
            breakStart,
            breakEnd,
            true,
            false
        );

        WorkTimeRequestDTO dto2 = new WorkTimeRequestDTO(
            "Commercial",
            true,
            initialTime,
            endTime,
            false,
            breakStart,
            breakEnd,
            true,
            false
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different DTOs")
    void shouldTestInequalityBetweenDifferentDTOs() {
        WorkTimeRequestDTO dto1 = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        WorkTimeRequestDTO dto2 = new WorkTimeRequestDTO(
            "Night Shift",
            true,
            LocalTime.of(22, 0),
            LocalTime.of(6, 0),
            true,
            LocalTime.of(2, 0),
            LocalTime.of(3, 0),
            false,
            true
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation")
    void shouldTestStringRepresentation() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("WorkTimeRequestDTO") || str.contains("Commercial"));
    }

    @Test
    @DisplayName("Should handle edge case times")
    void shouldHandleEdgeCaseTimes() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Full Day",
            true,
            LocalTime.of(0, 0),
            LocalTime.of(23, 59),
            false,
            LocalTime.of(11, 30),
            LocalTime.of(12, 30),
            true,
            false
        );

        assertNotNull(dto);
        assertEquals(LocalTime.of(0, 0), dto.initialTime());
        assertEquals(LocalTime.of(23, 59), dto.endTime());

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should handle inactive work time")
    void shouldHandleInactiveWorkTime() {
        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            "Commercial",
            false,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertFalse(dto.isActive());

        Set<ConstraintViolation<WorkTimeRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should test record field access")
    void shouldTestRecordFieldAccess() {
        String description = "Test Time";
        Boolean isActive = true;
        LocalTime initialTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        Boolean isOvernight = false;
        LocalTime initialBreakTime = LocalTime.of(12, 0);
        LocalTime endBreakTime = LocalTime.of(13, 0);
        Boolean requiresPunch = true;
        Boolean autoGeneratePunches = false;

        WorkTimeRequestDTO dto = new WorkTimeRequestDTO(
            description,
            isActive,
            initialTime,
            endTime,
            isOvernight,
            initialBreakTime,
            endBreakTime,
            requiresPunch,
            autoGeneratePunches
        );

        assertEquals(description, dto.description());
        assertEquals(isActive, dto.isActive());
        assertEquals(initialTime, dto.initialTime());
        assertEquals(endTime, dto.endTime());
        assertEquals(isOvernight, dto.isOvernight());
        assertEquals(initialBreakTime, dto.initialBreakTime());
        assertEquals(endBreakTime, dto.endBreakTime());
        assertEquals(requiresPunch, dto.requiresPunch());
        assertEquals(autoGeneratePunches, dto.autoGeneratePunches());
    }
}
