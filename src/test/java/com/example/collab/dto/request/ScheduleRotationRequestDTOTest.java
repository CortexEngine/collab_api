package com.example.collab.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("ScheduleRotationRequestDTO Tests")
class ScheduleRotationRequestDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Should create valid ScheduleRotationRequestDTO")
    void shouldCreateValidScheduleRotationRequestDTO() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3, 4, 5),
            List.of(true, true, true, true, true)
        );

        assertNotNull(dto);
        assertEquals(1L, dto.workSchedule());
        assertEquals(2L, dto.workTime());
        assertEquals(5, dto.dayIndexs().size());
        assertEquals(5, dto.workdays().size());
    }

    @Test
    @DisplayName("Should validate with valid day indexes within range")
    void shouldValidateWithValidDayIndexes() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 3, 4, 5, 6, 7),
            List.of(true, true, true, true, true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with null workSchedule")
    void shouldFailValidationWithNullWorkSchedule() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            null,
            1L,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("workSchedule")));
    }

    @Test
    @DisplayName("Should fail validation with null workTime")
    void shouldFailValidationWithNullWorkTime() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            null,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("workTime")));
    }

    @Test
    @DisplayName("Should fail validation with null dayIndexs")
    void shouldFailValidationWithNullDayIndexs() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            null,
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dayIndexs")));
    }

    @Test
    @DisplayName("Should fail validation with null workdays")
    void shouldFailValidationWithNullWorkdays() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 3),
            null
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("workdays")));
    }

    @Test
    @DisplayName("Should fail validation with empty dayIndexs")
    void shouldFailValidationWithEmptyDayIndexs() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(),
            List.of(true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dayIndexs")));
    }

    @Test
    @DisplayName("Should fail validation with empty workdays")
    void shouldFailValidationWithEmptyWorkdays() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1),
            List.of()
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("workdays")));
    }

    @Test
    @DisplayName("Should fail validation with day index below minimum")
    void shouldFailValidationWithDayIndexBelowMinimum() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(0, 1, 2),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with day index above maximum")
    void shouldFailValidationWithDayIndexAboveMaximum() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 8),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with negative workSchedule")
    void shouldFailValidationWithNegativeWorkSchedule() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            -1L,
            1L,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation with negative workTime")
    void shouldFailValidationWithNegativeWorkTime() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            -1L,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should test equality between two identical DTOs")
    void shouldTestEqualityBetweenIdenticalDTOs() {
        ScheduleRotationRequestDTO dto1 = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        ScheduleRotationRequestDTO dto2 = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different DTOs")
    void shouldTestInequalityBetweenDifferentDTOs() {
        ScheduleRotationRequestDTO dto1 = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        ScheduleRotationRequestDTO dto2 = new ScheduleRotationRequestDTO(
            1L,
            3L,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation")
    void shouldTestStringRepresentation() {
        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("ScheduleRotationRequestDTO") || str.contains("1") || str.contains("2"));
    }

    @Test
    @DisplayName("Should handle null dayIndexs element in list")
    void shouldHandleNullDayIndexsElement() {
        List<Integer> dayIndexs = new java.util.ArrayList<>();
        dayIndexs.add(null);
        dayIndexs.add(2);

        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            dayIndexs,
            List.of(true, true)
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should handle null workdays element in list")
    void shouldHandleNullWorkdaysElement() {
        List<Boolean> workdays = new java.util.ArrayList<>();
        workdays.add(true);
        workdays.add(null);

        ScheduleRotationRequestDTO dto = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2),
            workdays
        );

        Set<ConstraintViolation<ScheduleRotationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}
