package com.example.collab.domain.valueobject.contact;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email Value Object Tests")
class EmailTest {

    @Test
    @DisplayName("Should create Email with valid address")
    void shouldCreateEmailWithValidAddress() {

        // Arrange & Act
        Email email = new Email("john.doe@example.com");

        // Assert
        assertNotNull(email);
        assertEquals("john.doe@example.com", email.getEmail());

    }

    @Test
    @DisplayName("Should create Email with subdomain")
    void shouldCreateEmailWithSubdomain() {

        // Arrange & Act
        Email email = new Email("user@mail.company.com");

        // Assert
        assertNotNull(email);
        assertEquals("user@mail.company.com", email.getEmail());

    }

    @Test
    @DisplayName("Should create Email with plus sign")
    void shouldCreateEmailWithPlusSign() {

        // Arrange & Act
        Email email = new Email("user+tag@example.com");

        // Assert
        assertNotNull(email);
        assertEquals("user+tag@example.com", email.getEmail());

    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 
            new Email(null)
        );

        assertEquals("Email address must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when email is blank")
    void shouldThrowExceptionWhenEmailIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 
            new Email("")
        );
        
        assertEquals("Email address must be provided.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when email has no @ symbol")
    void shouldThrowExceptionWhenEmailHasNoAtSymbol() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Email("invalidemail.com")

        );

        assertTrue(exception.getMessage().contains("Invalid format"));

    }

    @Test
    @DisplayName("Should throw exception when email has no domain")
    void shouldThrowExceptionWhenEmailHasNoDomain() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Email("user@")

        );

        assertTrue(exception.getMessage().contains("Invalid format"));

    }

    @Test
    @DisplayName("Should throw exception when email has no local part")
    void shouldThrowExceptionWhenEmailHasNoLocalPart() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Email("@example.com")

        );

        assertTrue(exception.getMessage().contains("Invalid format"));

    }

    @Test
    @DisplayName("Should throw exception when email has multiple @ symbols")
    void shouldThrowExceptionWhenEmailHasMultipleAtSymbols() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> 

            new Email("user@@example.com")

        );

        assertTrue(exception.getMessage().contains("Invalid format"));

    }

    @Test
    @DisplayName("Should throw exception when email has spaces")
    void shouldThrowExceptionWhenEmailHasSpaces() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> 

            new Email("user name@example.com")

        );

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        Email email = new Email("test@example.com");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(email.getEmail());
        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> 

            email.getClass().getMethod("setEmail", String.class)

        );

    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {

        // Arrange
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");
        Email email3 = new Email("other@example.com");

        // Assert
        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertEquals(email1.hashCode(), email2.hashCode());

    }

    @Test
    @DisplayName("Should accept various valid email formats")
    void shouldAcceptVariousValidEmailFormats() {

        // Act & Assert
        assertDoesNotThrow(() -> new Email("simple@example.com"));
        assertDoesNotThrow(() -> new Email("very.common@example.com"));
        assertDoesNotThrow(() -> new Email("x@example.com"));
        assertDoesNotThrow(() -> new Email("long.email-address-with-hyphens@and.subdomains.example.com"));
        assertDoesNotThrow(() -> new Email("user+mailbox/department=shipping@example.com"));
        assertDoesNotThrow(() -> new Email("name@example.co.uk"));

    }
}
