package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VoterRegistration Value Object Tests")
class VoterRegistrationTest {

    @Test
    @DisplayName("Should create VoterRegistration with valid number")
    void shouldCreateVoterRegistrationWithValidNumber() {

        // Arrange & Act
        VoterRegistration voterReg = new VoterRegistration("123456789012");

        // Assert
        assertNotNull(voterReg);
        assertEquals("123456789012", voterReg.getVoteId());

    }

    @Test
    @DisplayName("Should create VoterRegistration and remove formatting characters")
    void shouldCreateVoterRegistrationAndRemoveFormattingCharacters() {

        // Arrange & Act
        VoterRegistration voterReg = new VoterRegistration("1234.5678.9012");

        // Assert
        assertNotNull(voterReg);
        assertEquals("123456789012", voterReg.getVoteId());

    }

    @Test
    @DisplayName("Should create VoterRegistration with spaces")
    void shouldCreateVoterRegistrationWithSpaces() {

        // Arrange & Act
        VoterRegistration voterReg = new VoterRegistration("1234 5678 9012");

        // Assert
        assertNotNull(voterReg);
        assertEquals("123456789012", voterReg.getVoteId());

    }

    @Test
    @DisplayName("Should create VoterRegistration with hyphens")
    void shouldCreateVoterRegistrationWithHyphens() {

        // Arrange & Act
        VoterRegistration voterReg = new VoterRegistration("1234-5678-9012");

        // Assert
        assertNotNull(voterReg);
        assertEquals("123456789012", voterReg.getVoteId());

    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration is null")
    void shouldThrowExceptionWhenVoterRegistrationIsNull() {
      
        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new VoterRegistration(null));

        assertEquals("Voter Registration Card must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration is blank")
    void shouldThrowExceptionWhenVoterRegistrationIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new VoterRegistration(""));

        assertEquals("Voter Registration Card must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration has only spaces")
    void shouldThrowExceptionWhenVoterRegistrationHasOnlySpaces() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new VoterRegistration("   "));

        assertEquals("Voter Registration Card must be provided.", exception.getMessage());
        
    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration has less than 12 digits")
    void shouldThrowExceptionWhenVoterRegistrationHasLessThan12Digits() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new VoterRegistration("12345678901"));

        assertEquals("Voter Registration Card must contain 12 digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration has more than 12 digits")
    void shouldThrowExceptionWhenVoterRegistrationHasMoreThan12Digits() {
        
      // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,() -> new VoterRegistration("1234567890123"));

        assertEquals("Voter Registration Card must contain 12 digits.", exception.getMessage());

    }

    @Test
    @DisplayName("Should accept VoterRegistration with all zeros")
    void shouldAcceptVoterRegistrationWithAllZeros() {
      
      // Arrange & Act
        VoterRegistration voterReg = new VoterRegistration("000000000000");

        // Assert
        assertNotNull(voterReg);
        assertEquals("000000000000", voterReg.getVoteId());
    
    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {
  
      // Arrange
        VoterRegistration voterReg1 = new VoterRegistration("123456789012");
        VoterRegistration voterReg2 = new VoterRegistration("123456789012");

        // Assert
        assertEquals(voterReg1, voterReg2);
        assertEquals(voterReg1.hashCode(), voterReg2.hashCode());
  
    }

    @Test
    @DisplayName("Should have different instances for different numbers")
    void shouldHaveDifferentInstancesForDifferentNumbers() {

      // Arrange
        VoterRegistration voterReg1 = new VoterRegistration("123456789012");
        VoterRegistration voterReg2 = new VoterRegistration("987654321098");

        // Assert
        assertNotEquals(voterReg1, voterReg2);
        assertNotEquals(voterReg1.hashCode(), voterReg2.hashCode());

     }

    @Test
    @DisplayName("Should handle formatted and unformatted VoterRegistration as equal")
    void shouldHandleFormattedAndUnformattedVoterRegistrationAsEqual() {
  
      // Arrange
        VoterRegistration voterReg1 = new VoterRegistration("1234.5678.9012");
        VoterRegistration voterReg2 = new VoterRegistration("123456789012");

        // Assert
        assertEquals(voterReg1, voterReg2);
        assertEquals(voterReg1.hashCode(), voterReg2.hashCode());
  
    }

    @Test
    @DisplayName("Should accept different valid VoterRegistration patterns")
    void shouldAcceptDifferentValidVoterRegistrationPatterns() {

      // Arrange & Act & Assert
        assertDoesNotThrow(() -> new VoterRegistration("123456789012"));
        assertDoesNotThrow(() -> new VoterRegistration("000011112222"));
        assertDoesNotThrow(() -> new VoterRegistration("999988887777"));

    }
}
