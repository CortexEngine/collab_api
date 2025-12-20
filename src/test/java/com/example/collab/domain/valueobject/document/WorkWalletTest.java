package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkWallet Value Object Tests")
class WorkWalletTest {

    @Test
    @DisplayName("Should create WorkWallet with valid number")
    void shouldCreateWorkWalletWithValidNumber() {

        // Arrange & Act
        WorkWallet workWallet = new WorkWallet("12345678901");

        // Assert
        assertNotNull(workWallet);
        assertEquals("12345678901", workWallet.getNumber());

    }

    @Test
    @DisplayName("Should create WorkWallet and remove formatting characters")
    void shouldCreateWorkWalletAndRemoveFormattingCharacters() {

        // Arrange & Act
        WorkWallet workWallet = new WorkWallet("1234567-8901");

        // Assert
        assertNotNull(workWallet);
        assertEquals("12345678901", workWallet.getNumber());

    }

    @Test
    @DisplayName("Should create WorkWallet with spaces and hyphens")
    void shouldCreateWorkWalletWithSpacesAndHyphens() {

        // Arrange & Act
        WorkWallet workWallet = new WorkWallet("1234567 8901");

        // Assert
        assertNotNull(workWallet);
        assertEquals("12345678901", workWallet.getNumber());

    }

    @Test
    @DisplayName("Should create WorkWallet with dots")
    void shouldCreateWorkWalletWithDots() {

        // Arrange & Act
        WorkWallet workWallet = new WorkWallet("1234.567.8901");

        // Assert
        assertNotNull(workWallet);
        assertEquals("12345678901", workWallet.getNumber());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet is null")
    void shouldThrowExceptionWhenWorkWalletIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet(null));

        assertEquals("Work Permit must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet is blank")
    void shouldThrowExceptionWhenWorkWalletIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet(""));

        assertEquals("Work Permit must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet has only spaces")
    void shouldThrowExceptionWhenWorkWalletHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet("   "));

        assertEquals("Work Permit must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet has less than 11 digits")
    void shouldThrowExceptionWhenWorkWalletHasLessThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet("1234567890"));

        assertEquals("The Work Permit must contain 11 digits (7 for the number and 4 for the series).", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet has more than 11 digits")
    void shouldThrowExceptionWhenWorkWalletHasMoreThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet("123456789012"));

        assertEquals("The Work Permit must contain 11 digits (7 for the number and 4 for the series).", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet contains all identical digits")
    void shouldThrowExceptionWhenWorkWalletContainsAllIdenticalDigits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet("11111111111"));

        assertEquals("A work permit cannot contain all the same digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet is all zeros")
    void shouldThrowExceptionWhenWorkWalletIsAllZeros() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new WorkWallet("00000000000"));

        assertEquals("A work permit cannot contain all the same digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        WorkWallet workWallet1 = new WorkWallet("12345678901");
        WorkWallet workWallet2 = new WorkWallet("12345678901");

        // Assert
        assertEquals(workWallet1, workWallet2);
        assertEquals(workWallet1.hashCode(), workWallet2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

        // Arrange
        WorkWallet workWallet1 = new WorkWallet("12345678901");
        WorkWallet workWallet2 = new WorkWallet("98765432109");

        // Assert
        assertNotEquals(workWallet1, workWallet2);
        assertNotEquals(workWallet1.hashCode(), workWallet2.hashCode());

    }

    @Test
    @DisplayName("Should handle formatted and unformatted WorkWallet as equal")
    void shouldHandleFormattedAndUnformattedWorkWalletAsEqual() {

        // Arrange
        WorkWallet workWallet1 = new WorkWallet("1234567-8901");
        WorkWallet workWallet2 = new WorkWallet("12345678901");

        // Assert
        assertEquals(workWallet1, workWallet2);
        assertEquals(workWallet1.hashCode(), workWallet2.hashCode());
        
    }
}
