package com.example.collab.dto.request;

import jakarta.validation.*;
import jakarta.validation.constraints.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("CollaboratorRequestDTO Validation Tests")
class CollaboratorRequestDTOTest {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Should validate collaborator request with all valid required fields")
    void shouldValidateCollaboratorRequestWithAllValidRequiredFields() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setBirthDate(LocalDate.of(1990, 5, 15));
        dto.setMaritalStatus("Single");
        dto.setNationality("Brazilian");
        dto.setEmail("john.doe@example.com");
        dto.setPhone("+55 11 987654321");
        dto.setAddress("123 Main St");
        dto.setPosition("Software Engineer");
        dto.setDepartment(101);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setAdmissionDate(LocalDate.now().plusDays(1));
        dto.setContractType("CLT");
        dto.setSalary(5000.0);
        dto.setRegistration(12345);
        dto.setWorkload("40h");
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("john.doe@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("046.797.650-38");
        dto.setRG("16.387.753-1");

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when name is blank")
    void shouldFailValidationWhenNameIsBlank() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("12345678901");
        dto.setRG("123456789");

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    @DisplayName("Should fail validation when name exceeds max size")
    void shouldFailValidationWhenNameExceedsMaxSize() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("A".repeat(121)); // Exceeds 120 characters
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("name") &&
            v.getMessage().contains("120")
        ));
    }

    @Test
    @DisplayName("Should fail validation when birth date is in the future")
    void shouldFailValidationWhenBirthDateIsInFuture() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setBirthDate(LocalDate.now().plusDays(1));
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("birthDate") &&
            v.getConstraintDescriptor().getAnnotation() instanceof Past
        ));
    }

    @Test
    @DisplayName("Should fail validation when email is invalid")
    void shouldFailValidationWhenEmailIsInvalid() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("invalid-email");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Should fail validation when admission date is in the past")
    void shouldFailValidationWhenAdmissionDateIsInPast() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setAdmissionDate(LocalDate.now().minusDays(1));
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("admissionDate") &&
            v.getConstraintDescriptor().getAnnotation() instanceof FutureOrPresent
        ));
    }

    @Test
    @DisplayName("Should fail validation when salary is negative")
    void shouldFailValidationWhenSalaryIsNegative() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setSalary(-1000.0);
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("salary")));
    }

    @Test
    @DisplayName("Should fail validation when registration is null")
    void shouldFailValidationWhenRegistrationIsNull() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setManager(false);
        dto.setSupportManager(false);
        // registration is null

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("registration")));
    }

    @Test
    @DisplayName("Should fail validation when bank is null")
    void shouldFailValidationWhenBankIsNull() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        // bank is null

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("bank")));
    }

    @Test
    @DisplayName("Should fail validation when CPF is null")
    void shouldFailValidationWhenCPFIsNull() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setRG("123456789");
        // CPF is null

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("CPF")));
    }

    @Test
    @DisplayName("Should fail validation when RG is null")
    void shouldFailValidationWhenRGIsNull() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("12345678901");
        // RG is null

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("RG")));
    }

    @Test
    @DisplayName("Should validate with optional fields as null")
    void shouldValidateWithOptionalFieldsAsNull() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("12345678901");
        dto.setRG("123456789");
        // Optional fields are null

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should set and get all fields correctly")
    void shouldSetAndGetAllFieldsCorrectly() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        String name = "Jane Smith";
        LocalDate birthDate = LocalDate.of(1985, 10, 20);
        String email = "jane.smith@example.com";
        Integer registration = 54321;
        Double salary = 7500.0;

        // Act
        dto.setName(name);
        dto.setBirthDate(birthDate);
        dto.setEmail(email);
        dto.setRegistration(registration);
        dto.setSalary(salary);
        dto.setManager(true);
        dto.setSupportManager(true);

        // Assert
        assertEquals(name, dto.getName());
        assertEquals(birthDate, dto.getBirthDate());
        assertEquals(email, dto.getEmail());
        assertEquals(registration, dto.getRegistration());
        assertEquals(salary, dto.getSalary());
        assertTrue(dto.isManager());
        assertTrue(dto.isSupportManager());
    }

    @Test
    @DisplayName("Should handle date edge cases")
    void shouldHandleDateEdgeCases() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setBirthDate(LocalDate.now().minusDays(1)); // Yesterday is valid for Past
        dto.setAdmissionDate(LocalDate.now()); // Today is valid for PastOrPresent
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("12345678901");
        dto.setRG("123456789");

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate manager and support manager flags")
    void shouldValidateManagerAndSupportManagerFlags() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("Manager User");
        dto.setRegistration(12345);
        dto.setManager(true);
        dto.setSupportManager(true);
        dto.setBank("001");
        dto.setAgency("1234");
        dto.setAccount("12345-6");
        dto.setTypeAccount("Corrente");
        dto.setPix("test@example.com");
        dto.setWorkWallet("12345678");
        dto.setCPF("12345678901");
        dto.setRG("123456789");

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
        assertTrue(dto.isManager());
        assertTrue(dto.isSupportManager());
    }

    @Test
    @DisplayName("Should fail validation when bank code exceeds max size")
    void shouldFailValidationWhenBankCodeExceedsMaxSize() {
        // Arrange
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO();
        dto.setName("John Doe");
        dto.setRegistration(12345);
        dto.setManager(false);
        dto.setSupportManager(false);
        dto.setBank("0001"); // Exceeds 3 characters

        // Act
        Set<ConstraintViolation<CollaboratorRequestDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("bank") &&
            v.getMessage().contains("3")
        ));
    }
}
