package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CNPJ Value Object Tests")
class CNPJTest {

    @Test
    @DisplayName("Should create CNPJ with valid number")
    void shouldCreateCNPJWithValidNumber() {

        // Arrange & Act
        CNPJ cnpj = new CNPJ("11222333000181");

        // Assert
        assertNotNull(cnpj);
        assertEquals("11222333000181", cnpj.getCnpj());

    }

    @Test
    @DisplayName("Should create CNPJ and remove formatting characters")
    void shouldCreateCNPJAndRemoveFormattingCharacters() {

        // Arrange & Act
        CNPJ cnpj = new CNPJ("11.222.333/0001-81");

        // Assert
        assertNotNull(cnpj);
        assertEquals("11222333000181", cnpj.getCnpj());

    }

    @Test
    @DisplayName("Should create CNPJ with spaces and dots")
    void shouldCreateCNPJWithSpacesAndDots() {

        // Arrange & Act
        CNPJ cnpj = new CNPJ("11 222 333 0001 81");

        // Assert
        assertNotNull(cnpj);
        assertEquals("11222333000181", cnpj.getCnpj());

    }

    @Test
    @DisplayName("Should validate CNPJ check digits correctly")
    void shouldValidateCNPJCheckDigitsCorrectly() {

        // Arrange & Act & Assert
        assertDoesNotThrow(() -> new CNPJ("11222333000181"));
        assertDoesNotThrow(() -> new CNPJ("34028316000103"));

    }

    @Test
    @DisplayName("Should throw exception when CNPJ is null")
    void shouldThrowExceptionWhenCNPJIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ(null));

        assertEquals("CNPJ must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNPJ is blank")
    void shouldThrowExceptionWhenCNPJIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ(""));

        assertEquals("CNPJ must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNPJ has only spaces")
    void shouldThrowExceptionWhenCNPJHasOnlySpaces() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ("   "));

        assertEquals("CNPJ must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNPJ has less than 14 digits")
    void shouldThrowExceptionWhenCNPJHasLessThan14Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ("1122233300018"));

        assertEquals("CNPJ must contain 14 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNPJ has more than 14 digits")
    void shouldThrowExceptionWhenCNPJHasMoreThan14Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ("112223330001811"));

        assertEquals("CNPJ must contain 14 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNPJ has invalid check digits")
    void shouldThrowExceptionWhenCNPJHasInvalidCheckDigits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ("11222333000180"));

        assertTrue(exception.getMessage().contains("Invalid CNPJ"));

    }

    @Test
    @DisplayName("Should throw exception when CNPJ contains letters")
    void shouldThrowExceptionWhenCNPJContainsLetters() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNPJ("1122233300018A"));

        assertEquals("CNPJ must contain 14 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        CNPJ cnpj1 = new CNPJ("11222333000181");
        CNPJ cnpj2 = new CNPJ("11222333000181");

        // Assert
        assertEquals(cnpj1, cnpj2);
        assertEquals(cnpj1.hashCode(), cnpj2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

        // Arrange
        CNPJ cnpj1 = new CNPJ("11222333000181");
        CNPJ cnpj2 = new CNPJ("34028316000103");

        // Assert
        assertNotEquals(cnpj1, cnpj2);
        assertNotEquals(cnpj1.hashCode(), cnpj2.hashCode());

    }

    @Test
    @DisplayName("Should handle formatted and unformatted CNPJ as equal")
    void shouldHandleFormattedAndUnformattedCNPJAsEqual() {

        // Arrange
        CNPJ cnpj1 = new CNPJ("11.222.333/0001-81");
        CNPJ cnpj2 = new CNPJ("11222333000181");

        // Assert
        assertEquals(cnpj1, cnpj2);
        assertEquals(cnpj1.hashCode(), cnpj2.hashCode());

    }

    @Test
    @DisplayName("Should validate multiple valid CNPJs")
    void shouldValidateMultipleValidCNPJs() {

        // Arrange & Act & Assert
        assertDoesNotThrow(() -> new CNPJ("11222333000181"));
        assertDoesNotThrow(() -> new CNPJ("34028316000103"));
        assertDoesNotThrow(() -> new CNPJ("00000000000191"));
        
    }
}
