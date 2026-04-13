package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PIX Value Object Tests")
class PIXTest {

    @Test
    @DisplayName("Should create PIX with valid CPF key")
    void shouldCreatePIXWithValidCPFKey() {

        // Arrange & Act
        PIX pix = new PIX("12345678909");

        // Assert
        assertNotNull(pix);
        assertEquals("12345678909", pix.getKey());
    }

    @Test
    @DisplayName("Should create PIX with valid email key")
    void shouldCreatePIXWithValidEmailKey() {

        // Arrange & Act
        PIX pix = new PIX("user@example.com");

        // Assert
        assertNotNull(pix);
        assertEquals("user@example.com", pix.getKey());
    }

    @Test
    @DisplayName("Should create PIX with valid phone key")
    void shouldCreatePIXWithValidPhoneKey() {

        // Arrange & Act
        PIX pix = new PIX("+551191234-5678");

        // Assert
        assertNotNull(pix);
        assertEquals("+551191234-5678", pix.getKey());
    }

    @Test
    @DisplayName("Should create PIX with valid random key")
    void shouldCreatePIXWithValidRandomKey() {

        // Arrange & Act
        PIX pix = new PIX("123e4567-e89b-12d3-a456-426614174000");

        // Assert
        assertNotNull(pix);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", pix.getKey());
    }

    @Test
    @DisplayName("Should throw exception when PIX key is null")
    void shouldThrowExceptionWhenPIXKeyIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new PIX(null)

        );

        assertEquals("PIX key must be provided.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when PIX key is blank")
    void shouldThrowExceptionWhenPIXKeyIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new PIX("")

        );

        assertEquals("PIX key must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when PIX key is invalid")
    void shouldThrowExceptionWhenPIXKeyIsInvalid() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new PIX("invalid-key")

        );

        assertEquals("Invalid PIX key", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception for invalid CPF as PIX key")
    void shouldThrowExceptionForInvalidCPFAsPIXKey() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> 

            new PIX("00000000000") // Invalid CPF

        );

    }

    @Test
    @DisplayName("Should throw exception for invalid email as PIX key")
    void shouldThrowExceptionForInvalidEmailAsPIXKey() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> 

            new PIX("invalid-email") // Invalid email format

        );

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        PIX pix = new PIX("user@example.com");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(pix.getKey());

        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> 

            pix.getClass().getMethod("setKey", String.class)

        );

    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {

        // Arrange
        PIX pix1 = new PIX("user@example.com");
        PIX pix2 = new PIX("user@example.com");
        PIX pix3 = new PIX("other@example.com");

        // Assert
        assertEquals(pix1, pix2);
        assertNotEquals(pix1, pix3);
        assertEquals(pix1.hashCode(), pix2.hashCode());
    }

    @Test
    @DisplayName("Should accept different types of valid PIX keys")
    void shouldAcceptDifferentTypesOfValidPIXKeys() {

        // Act & Assert - CPF
        assertDoesNotThrow(() -> new PIX("52998224725"));
        
        // Email
        assertDoesNotThrow(() -> new PIX("user@example.com"));
        assertDoesNotThrow(() -> new PIX("test.user@mail.com"));
        
        // Phone
        assertDoesNotThrow(() -> new PIX("+55 11 98765-4321"));
        assertDoesNotThrow(() -> new PIX("+55 21 99876-5432"));
        
        // Random Key (UUID format)
        assertDoesNotThrow(() -> new PIX("a1b2c3d4-e5f6-7890-abcd-ef1234567890"));
    }
}
