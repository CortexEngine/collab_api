package com.example.collab.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("DepartmentRequestDTO Validation Tests")
class DepartmentRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should validate with required valid fields")
    void shouldValidateWithRequiredValidFields() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO(
            "Engineering",
            101,
            LocalDate.now().minusDays(1),
            LocalDate.now().plusDays(10),
            1001,
            List.of(1002, 1003),
            List.of(2001, 2002)
        );

        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail when required fields are null")
    void shouldFailWhenRequiredFieldsAreNull() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO(
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        Set<ConstraintViolation<DepartmentRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("number")));
    }
}
