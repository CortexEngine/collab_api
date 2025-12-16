package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CPF Value Object Tests")
class CPFTest {

    @Test
    @DisplayName("Should create CPF with valid number")
    void shouldCreateCPFWithValidNumber() {

        // Arrange & Act
        CPF cpf = new CPF("12345678909");

        // Assert
        assertNotNull(cpf);
        assertEquals("12345678909", cpf.getCpf());

    }

    @Test
    @DisplayName("Should create CPF with formatted number")
    void shouldCreateCPFWithFormattedNumber() {

        // Arrange & Act
        CPF cpf = new CPF("123.456.789-09");

        // Assert
        assertNotNull(cpf);
        assertEquals("12345678909", cpf.getCpf());

    }

    @Test
    @DisplayName("Should throw exception when CPF is null")
    void shouldThrowExceptionWhenCPFIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () ->  new CPF(null));

        assertEquals("CPF must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CPF is blank")
    void shouldThrowExceptionWhenCPFIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new CPF(""));

        assertEquals("CPF must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CPF has less than 11 digits")
    void shouldThrowExceptionWhenCPFHasLessThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new CPF("1234567890"));

        assertEquals("CPF must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CPF has more than 11 digits")
    void shouldThrowExceptionWhenCPFHasMoreThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new CPF("123456789012"));

        assertEquals("CPF must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CPF has all identical digits")
    void shouldThrowExceptionWhenCPFHasAllIdenticalDigits() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new CPF("11111111111"));
        assertThrows(InvalidDocumentException.class, () -> new CPF("00000000000"));
        assertThrows(InvalidDocumentException.class, () -> new CPF("99999999999"));

    }

    @Test
    @DisplayName("Should throw exception when CPF has invalid verification digits")
    void shouldThrowExceptionWhenCPFHasInvalidVerificationDigits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new CPF("12345678900"));

        assertTrue(exception.getMessage().contains("Invalid CPF"));

    }

    @Test
    @DisplayName("Should remove formatting from CPF")
    void shouldRemoveFormattingFromCPF() {

        // Arrange & Act
        CPF cpf1 = new CPF("123.456.789-09");
        CPF cpf2 = new CPF("123-456-789-09");
        CPF cpf3 = new CPF("123 456 789 09");

        // Assert
        assertEquals("12345678909", cpf1.getCpf());
        assertEquals("12345678909", cpf2.getCpf());
        assertEquals("12345678909", cpf3.getCpf());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        CPF cpf = new CPF("12345678909");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(cpf.getCpf());
        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> cpf.getClass().getMethod("setCpf", String.class));

    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {

        // Arrange
        CPF cpf1 = new CPF("12345678909");
        CPF cpf2 = new CPF("123.456.789-09");
        CPF cpf3 = new CPF("98765432100");

        // Assert
        assertEquals(cpf1, cpf2); // Same CPF, different formats
        assertNotEquals(cpf1, cpf3); // Different CPFs
        assertEquals(cpf1.hashCode(), cpf2.hashCode());

    }

    @Test
    @DisplayName("Should validate real CPF numbers")
    void shouldValidateRealCPFNumbers() {

        // Valid CPFs
        assertDoesNotThrow(() -> new CPF("11144477735"));
        assertDoesNotThrow(() -> new CPF("111.444.777-35"));
        
        // Invalid CPFs
        assertThrows(InvalidDocumentException.class, () -> new CPF("11144477736"));
        assertThrows(InvalidDocumentException.class, () -> new CPF("111.444.777-36"));
        
    }
}
