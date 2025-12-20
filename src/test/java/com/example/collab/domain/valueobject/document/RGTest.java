package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RG Value Object Tests")
class RGTest {

    @Test
    @DisplayName("Should create RG with valid number")
    void shouldCreateRGWithValidNumber() {

        // Arrange & Act
        RG rg = new RG("123456789");

        // Assert
        assertNotNull(rg);
        assertEquals("123456789", rg.getRg());

    }

    @Test
    @DisplayName("Should create RG and remove formatting characters")
    void shouldCreateRGAndRemoveFormattingCharacters() {

        // Arrange & Act
        RG rg = new RG("12.345.678-9");

        // Assert
        assertNotNull(rg);
        assertEquals("123456789", rg.getRg());

    }

    @Test
    @DisplayName("Should create RG with minimum 5 digits")
    void shouldCreateRGWithMinimum5Digits() {

        // Arrange & Act
        RG rg = new RG("12345");

        // Assert
        assertNotNull(rg);
        assertEquals("12345", rg.getRg());

    }

    @Test
    @DisplayName("Should create RG with maximum 12 digits")
    void shouldCreateRGWithMaximum12Digits() {

        // Arrange & Act
        RG rg = new RG("123456789012");

        // Assert
        assertNotNull(rg);
        assertEquals("123456789012", rg.getRg());

    }

    @Test
    @DisplayName("Should create RG with different formats")
    void shouldCreateRGWithDifferentFormats() {

        // Arrange & Act
        RG rg1 = new RG("1.234.567");
        RG rg2 = new RG("12-345-678-9");
        RG rg3 = new RG("12 345 678 9");

        // Assert
        assertEquals("1234567", rg1.getRg());
        assertEquals("123456789", rg2.getRg());
        assertEquals("123456789", rg3.getRg());

    }

    @Test
    @DisplayName("Should throw exception when RG is null")
    void shouldThrowExceptionWhenRGIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG(null)
        );

        assertEquals("RG must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG is blank")
    void shouldThrowExceptionWhenRGIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG(""));

        assertEquals("RG must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG has only spaces")
    void shouldThrowExceptionWhenRGHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG("   "));

        assertEquals("RG must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG has less than 5 digits")
    void shouldThrowExceptionWhenRGHasLessThan5Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG("1234"));

        assertEquals("RG must have between 5 and 12 digits after formatting", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG has more than 12 digits")
    void shouldThrowExceptionWhenRGHasMoreThan12Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG("1234567890123"));

        assertEquals("RG must have between 5 and 12 digits after formatting", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG contains all identical digits")
    void shouldThrowExceptionWhenRGContainsAllIdenticalDigits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG("11111111111"));

        assertEquals("RG must not contain all identical digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when RG is all zeros")
    void shouldThrowExceptionWhenRGIsAllZeros() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new RG("000000000"));

        assertEquals("RG must not contain all identical digits", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        RG rg1 = new RG("123456789");
        RG rg2 = new RG("123456789");

        // Assert
        assertEquals(rg1, rg2);
        assertEquals(rg1.hashCode(), rg2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

        // Arrange
        RG rg1 = new RG("123456789");
        RG rg2 = new RG("987654321");

        // Assert
        assertNotEquals(rg1, rg2);
        assertNotEquals(rg1.hashCode(), rg2.hashCode());

    }

    @Test
    @DisplayName("Should handle formatted and unformatted RG as equal")
    void shouldHandleFormattedAndUnformattedRGAsEqual() {

        // Arrange
        RG rg1 = new RG("12.345.678-9");
        RG rg2 = new RG("123456789");

        // Assert
        assertEquals(rg1, rg2);
        assertEquals(rg1.hashCode(), rg2.hashCode());
        
    }
}
