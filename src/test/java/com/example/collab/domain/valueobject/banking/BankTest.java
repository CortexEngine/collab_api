package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bank Value Object Tests")
class BankTest {

    @Test
    @DisplayName("Should create Bank with valid 3-digit code")
    void shouldCreateBankWithValid3DigitCode() {
        // Arrange & Act
        Bank bank = new Bank("001");

        // Assert
        assertNotNull(bank);
        assertEquals("001", bank.getCode());
    }

    @Test
    @DisplayName("Should create Bank with different valid codes")
    void shouldCreateBankWithDifferentValidCodes() {
        // Act & Assert
        assertDoesNotThrow(() -> new Bank("001")); // Banco do Brasil
        assertDoesNotThrow(() -> new Bank("104")); // Caixa Econômica Federal
        assertDoesNotThrow(() -> new Bank("237")); // Bradesco
        assertDoesNotThrow(() -> new Bank("341")); // Itaú
        assertDoesNotThrow(() -> new Bank("033")); // Santander
    }

    @Test
    @DisplayName("Should throw exception when bank code is null")
    void shouldThrowExceptionWhenBankCodeIsNull() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Bank(null)

        );

        assertEquals("The bank code must be provided.", exception.getMessage());
        
    }

    @Test
    @DisplayName("Should throw exception when bank code is blank")
    void shouldThrowExceptionWhenBankCodeIsBlank() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Bank("")

        );

        assertEquals("The bank code must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when bank code has less than 3 digits")
    void shouldThrowExceptionWhenBankCodeHasLessThan3Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Bank("01")

        );

        assertEquals("The bank code must contain exactly 3 digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when bank code has more than 3 digits")
    void shouldThrowExceptionWhenBankCodeHasMoreThan3Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Bank("0012")

        );

        assertEquals("The bank code must contain exactly 3 digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when bank code contains letters")
    void shouldThrowExceptionWhenBankCodeContainsLetters() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Bank("A01")

        );

        assertEquals("The bank code must contain exactly 3 digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when bank code contains special characters")
    void shouldThrowExceptionWhenBankCodeContainsSpecialCharacters() {
        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new Bank("0-1"));
        assertThrows(InvalidDocumentException.class, () -> new Bank("0.1"));
        assertThrows(InvalidDocumentException.class, () -> new Bank("0@1"));
    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {
        // Arrange
        Bank bank = new Bank("001");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(bank.getCode());

        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> 

            bank.getClass().getMethod("setCode", String.class)

        );

    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        Bank bank1 = new Bank("001");
        Bank bank2 = new Bank("001");
        Bank bank3 = new Bank("237");

        // Assert
        assertEquals(bank1, bank2);
        assertNotEquals(bank1, bank3);
        assertEquals(bank1.hashCode(), bank2.hashCode());
    }

    @Test
    @DisplayName("Should accept codes starting with zero")
    void shouldAcceptCodesStartingWithZero() {
        // Act & Assert
        assertDoesNotThrow(() -> new Bank("001"));
        assertDoesNotThrow(() -> new Bank("033"));
        assertDoesNotThrow(() -> new Bank("077"));
    }
}
