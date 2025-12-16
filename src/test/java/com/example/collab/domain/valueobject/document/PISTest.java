package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PIS Value Object Tests")
class PISTest {

    @Test
    @DisplayName("Should create PIS with valid number")
    void shouldCreatePISWithValidNumber() {

        // Arrange & Act
        PIS pis = new PIS("14098188135");

        // Assert
        assertNotNull(pis);
        assertEquals("14098188135", pis.getPis());

    }

    @Test
    @DisplayName("Should create PIS and remove formatting characters")
    void shouldCreatePISAndRemoveFormattingCharacters() {

        // Arrange & Act
        PIS pis = new PIS("201.05173.89-9");

        // Assert
        assertNotNull(pis);
        assertEquals("20105173899", pis.getPis());

    }

    @Test
    @DisplayName("Should create PIS with spaces and dots")
    void shouldCreatePISWithSpacesAndDots() {

        // Arrange & Act
        PIS pis = new PIS("201 05173 89 9");

        // Assert
        assertNotNull(pis);
        assertEquals("20105173899", pis.getPis());
    }

    @Test
    @DisplayName("Should validate PIS check digit correctly")
    void shouldValidatePISCheckDigitCorrectly() {

        // Arrange & Act & Assert
        assertDoesNotThrow(() -> new PIS("20105173899"));
        assertDoesNotThrow(() -> new PIS("40466918770"));

    }

    @Test
    @DisplayName("Should throw exception when PIS is null")
    void shouldThrowExceptionWhenPISIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS(null));

        assertEquals("PIS must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIS is blank")
    void shouldThrowExceptionWhenPISIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS(""));

        assertEquals("PIS must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIS has only spaces")
    void shouldThrowExceptionWhenPISHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS("   "));

        assertEquals("PIS must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIS has less than 11 digits")
    void shouldThrowExceptionWhenPISHasLessThan11Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS("1234567890"));

        assertEquals("PIS must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIS has more than 11 digits")
    void shouldThrowExceptionWhenPISHasMoreThan11Digits() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS("123456789012"));

        assertEquals("PIS must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIS has invalid check digit")
    void shouldThrowExceptionWhenPISHasInvalidCheckDigit() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS("12082043689"));

        assertTrue(exception.getMessage().contains("Invalid PIS"));
        
    }

    @Test
    @DisplayName("Should throw exception when PIS contains letters")
    void shouldThrowExceptionWhenPISContainsLetters() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new PIS("1208204368A"));

        assertEquals("PIS must contain 11 digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        PIS pis1 = new PIS("99389572038");
        PIS pis2 = new PIS("99389572038");

        // Assert
        assertEquals(pis1, pis2);
        assertEquals(pis1.hashCode(), pis2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {
        
      // Arrange
        PIS pis1 = new PIS("99389572038");
        PIS pis2 = new PIS("59653534027");

        // Assert
        assertNotEquals(pis1, pis2);
        assertNotEquals(pis1.hashCode(), pis2.hashCode());
    
    }

    @Test
    @DisplayName("Should handle formatted and unformatted PIS as equal")
    void shouldHandleFormattedAndUnformattedPISAsEqual() {
  
      // Arrange
        PIS pis1 = new PIS("146.00770.29-0");
        PIS pis2 = new PIS("14600770290");

        // Assert
        assertEquals(pis1, pis2);
        assertEquals(pis1.hashCode(), pis2.hashCode());
  
    }
}
