package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CNH Value Object Tests")
class CNHTest {

    @Test
    @DisplayName("Should create CNH with valid number")
    void shouldCreateCNHWithValidNumber() {

        // Arrange & Act
        CNH cnh = new CNH("53580275390");

        // Assert
        assertNotNull(cnh);
        assertEquals("53580275390", cnh.getCnh());

    }

    @Test
    @DisplayName("Should create CNH and remove formatting characters")
    void shouldCreateCNHAndRemoveFormattingCharacters() {

        // Arrange & Act
        CNH cnh = new CNH("535.802.753-90");

        // Assert
        assertNotNull(cnh);
        assertEquals("53580275390", cnh.getCnh());

    }

    @Test
    @DisplayName("Should create CNH with spaces and hyphens")
    void shouldCreateCNHWithSpacesAndHyphens() {

        // Arrange & Act
        CNH cnh = new CNH("535 802 753-90");

        // Assert
        assertNotNull(cnh);
        assertEquals("53580275390", cnh.getCnh());

    }

    @Test
    @DisplayName("Should validate CNH check digits correctly")
    void shouldValidateCNHCheckDigitsCorrectly() {

        // Arrange & Act & Assert
        assertDoesNotThrow(() -> new CNH("53580275390"));
        assertDoesNotThrow(() -> new CNH("01234567895"));

    }

    @Test
    @DisplayName("Should throw exception when CNH is null")
    void shouldThrowExceptionWhenCNHIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH(null));

        assertEquals("Driver's license must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNH is blank")
    void shouldThrowExceptionWhenCNHIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH(""));

        assertEquals("Driver's license must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNH has only spaces")
    void shouldThrowExceptionWhenCNHHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH("   "));

        assertEquals("Driver's license must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNH has less than 11 digits")
    void shouldThrowExceptionWhenCNHHasLessThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH("1234567890"));

        assertEquals("Driver's license must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when CNH has more than 11 digits")
    void shouldThrowExceptionWhenCNHHasMoreThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH("123456789012"));

        assertEquals("Driver's license must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should accept valid CNH numbers")
    void shouldAcceptValidCNHNumbers() {

        // Arrange & Act & Assert
        assertDoesNotThrow(() -> new CNH("12345678901"));
        assertDoesNotThrow(() -> new CNH("98765432109"));
        
    }

    @Test
    @DisplayName("Should throw exception when CNH contains letters")
    void shouldThrowExceptionWhenCNHContainsLetters() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new CNH("1234567890A"));

        assertEquals("Driver's license must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        CNH cnh1 = new CNH("53580275390");
        CNH cnh2 = new CNH("53580275390");

        // Assert
        assertEquals(cnh1, cnh2);
        assertEquals(cnh1.hashCode(), cnh2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

        // Arrange
        CNH cnh1 = new CNH("53580275390");
        CNH cnh2 = new CNH("01234567895");

        // Assert
        assertNotEquals(cnh1, cnh2);
        assertNotEquals(cnh1.hashCode(), cnh2.hashCode());

    }

    @Test
    @DisplayName("Should handle formatted and unformatted CNH as equal")
    void shouldHandleFormattedAndUnformattedCNHAsEqual() {

        // Arrange
        CNH cnh1 = new CNH("535.802.753-90");
        CNH cnh2 = new CNH("53580275390");

        // Assert
        assertEquals(cnh1, cnh2);
        assertEquals(cnh1.hashCode(), cnh2.hashCode());

    }
}
