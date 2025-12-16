package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agency Value Object Tests")
class AgencyTest {

    @Test
    @DisplayName("Should create Agency with valid 4-digit number")
    void shouldCreateAgencyWithValidFourDigitNumber() {
      
        // Arrange & Act
        Agency agency = new Agency("1234");

        // Assert
        assertNotNull(agency);
        assertEquals("1234", agency.getNumber());

    }

    @Test
    @DisplayName("Should create Agency with valid number and check digit")
    void shouldCreateAgencyWithValidNumberAndCheckDigit() {

        // Arrange & Act
        Agency agency = new Agency("1234-5");

        // Assert
        assertNotNull(agency);
        assertEquals("1234-5", agency.getNumber());

    }

    @Test
    @DisplayName("Should create Agency with zeros")
    void shouldCreateAgencyWithZeros() {

        // Arrange & Act
        Agency agency = new Agency("0000");

        // Assert
        assertNotNull(agency);
        assertEquals("0000", agency.getNumber());

    }

    @Test
    @DisplayName("Should create Agency with zero check digit")
    void shouldCreateAgencyWithZeroCheckDigit() {

        // Arrange & Act
        Agency agency = new Agency("9876-0");

        // Assert
        assertNotNull(agency);
        assertEquals("9876-0", agency.getNumber());

    }

    @Test
    @DisplayName("Should throw exception when agency number is null")
    void shouldThrowExceptionWhenAgencyNumberIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency(null)

        );

        assertEquals("The agency number must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number is blank")
    void shouldThrowExceptionWhenAgencyNumberIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("")

        );

        assertEquals("The agency number must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number has only spaces")
    void shouldThrowExceptionWhenAgencyNumberHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("   ")

        );

        assertEquals("The agency number must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number has less than 4 digits")
    void shouldThrowExceptionWhenAgencyNumberHasLessThanFourDigits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("123")

        );

        assertEquals("The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number has more than 4 digits without check digit")
    void shouldThrowExceptionWhenAgencyNumberHasMoreThanFourDigitsWithoutCheckDigit() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("12345")

        );

        assertEquals("The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number contains letters")
    void shouldThrowExceptionWhenAgencyNumberContainsLetters() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(
          
                InvalidDocumentException.class,() -> new Agency("12A4")

        );

        assertEquals("The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number has invalid format with check digit")
    void shouldThrowExceptionWhenAgencyNumberHasInvalidFormatWithCheckDigit() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("1234-AB")

        );

        assertEquals("The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when agency number has multiple check digits")
    void shouldThrowExceptionWhenAgencyNumberHasMultipleCheckDigits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new Agency("1234-56")

        );

        assertEquals("The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        Agency agency1 = new Agency("1234");
        Agency agency2 = new Agency("1234");

        // Assert
        assertEquals(agency1, agency2);
        assertEquals(agency1.hashCode(), agency2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

        // Arrange
        Agency agency1 = new Agency("1234");
        Agency agency2 = new Agency("5678");

        // Assert
        assertNotEquals(agency1, agency2);
        assertNotEquals(agency1.hashCode(), agency2.hashCode());

    }
}
