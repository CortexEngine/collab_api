package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account Value Object Tests")
class AccountTest {

    @Test
    @DisplayName("Should create Account with valid number without check digit")
    void shouldCreateAccountWithValidNumberWithoutCheckDigit() {
        // Arrange & Act
        Account account = new Account("123456");

        // Assert
        assertNotNull(account);
        assertEquals("123456", account.getNumber());

    }

    @Test
    @DisplayName("Should create Account with valid number with check digit")
    void shouldCreateAccountWithValidNumberWithCheckDigit() {
        // Arrange & Act
        Account account = new Account("123456-7");

        // Assert
        assertNotNull(account);
        assertEquals("123456-7", account.getNumber());

    }

    @Test
    @DisplayName("Should create Account with 12 digits")
    void shouldCreateAccountWith12Digits() {
        // Arrange & Act
        Account account = new Account("123456789012");

        // Assert
        assertNotNull(account);
        assertEquals("123456789012", account.getNumber());

    }

    @Test
    @DisplayName("Should create Account with 12 digits and check digit")
    void shouldCreateAccountWith12DigitsAndCheckDigit() {
        // Arrange & Act
        Account account = new Account("123456789012-3");

        // Assert
        assertNotNull(account);
        assertEquals("123456789012-3", account.getNumber());

    }

    @Test
    @DisplayName("Should throw exception when account number is null")
    void shouldThrowExceptionWhenAccountNumberIsNull() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Account(null)

        );

        assertEquals("Account number must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when account number is blank")
    void shouldThrowExceptionWhenAccountNumberIsBlank() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Account("")

        );

        assertEquals("Account number must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when account has less than 6 digits")
    void shouldThrowExceptionWhenAccountHasLessThan6Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Account("12345")

        );

        assertTrue(exception.getMessage().contains("must have between 6 and 12 digits"));

    }

    @Test
    @DisplayName("Should throw exception when account has more than 12 digits")
    void shouldThrowExceptionWhenAccountHasMoreThan12Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Account("1234567890123")

        );

        assertTrue(exception.getMessage().contains("must have between 6 and 12 digits"));

    }

    @Test
    @DisplayName("Should throw exception when check digit has more than one digit")
    void shouldThrowExceptionWhenCheckDigitHasMoreThanOneDigit() {
        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> 

            new Account("123456-78")

        );

    }

    @Test
    @DisplayName("Should throw exception when account contains letters")
    void shouldThrowExceptionWhenAccountContainsLetters() {
        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> 

            new Account("12345A")

        );

    }

    @Test
    @DisplayName("Should throw exception when account has invalid format")
    void shouldThrowExceptionWhenAccountHasInvalidFormat() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new Account("123-456-7"));
        assertThrows(InvalidDocumentException.class, () -> new Account("123.456"));
        assertThrows(InvalidDocumentException.class, () -> new Account("123 456"));

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {
        // Arrange
        Account account = new Account("123456-7");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(account.getNumber());
        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> 

            account.getClass().getMethod("setNumber", String.class)
            
        );
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        Account account1 = new Account("123456-7");
        Account account2 = new Account("123456-7");
        Account account3 = new Account("654321-9");

        // Assert
        assertEquals(account1, account2);
        assertNotEquals(account1, account3);
        assertEquals(account1.hashCode(), account2.hashCode());
    }

    @Test
    @DisplayName("Should accept various valid account formats")
    void shouldAcceptVariousValidAccountFormats() {
        // Act & Assert
        assertDoesNotThrow(() -> new Account("123456"));
        assertDoesNotThrow(() -> new Account("1234567"));
        assertDoesNotThrow(() -> new Account("12345678"));
        assertDoesNotThrow(() -> new Account("123456789"));
        assertDoesNotThrow(() -> new Account("1234567890"));
        assertDoesNotThrow(() -> new Account("12345678901"));
        assertDoesNotThrow(() -> new Account("123456789012"));
        assertDoesNotThrow(() -> new Account("123456-7"));
        assertDoesNotThrow(() -> new Account("123456789-0"));
        assertDoesNotThrow(() -> new Account("123456789012-3"));
    }
}
