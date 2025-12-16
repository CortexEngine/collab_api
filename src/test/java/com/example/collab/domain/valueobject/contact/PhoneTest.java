package com.example.collab.domain.valueobject.contact;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Phone Value Object Tests")
class PhoneTest {

    @Test
    @DisplayName("Should create Phone with valid national mobile format")
    void shouldCreatePhoneWithValidNationalMobileFormat() {

        // Arrange & Act
        Phone phone = new Phone("(11) 98765-4321");

        // Assert
        assertNotNull(phone);
        assertEquals("(11) 98765-4321", phone.getNumber());

    }

    @Test
    @DisplayName("Should create Phone with valid national landline format")
    void shouldCreatePhoneWithValidNationalLandlineFormat() {

        // Arrange & Act
        Phone phone = new Phone("(11) 3456-7890");

        // Assert
        assertNotNull(phone);
        assertEquals("(11) 3456-7890", phone.getNumber());

    }

    @Test
    @DisplayName("Should create Phone with valid international format")
    void shouldCreatePhoneWithValidInternationalFormat() {

        // Arrange & Act
        Phone phone = new Phone("+55 11 98765-4321");

        // Assert
        assertNotNull(phone);
        assertEquals("+55 11 98765-4321", phone.getNumber());

    }

    @Test
    @DisplayName("Should create Phone with international mobile format")
    void shouldCreatePhoneWithInternationalMobileFormat() {

        // Arrange & Act
        Phone phone = new Phone("+55 11 98765-4321");

        // Assert
        assertNotNull(phone);
        assertEquals("+55 11 98765-4321", phone.getNumber());

    }

    @Test
    @DisplayName("Should throw exception when phone is null")
    void shouldThrowExceptionWhenPhoneIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new Phone(null));

        assertEquals("Phone number must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when phone is blank")
    void shouldThrowExceptionWhenPhoneIsBlank() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new Phone(""));

        assertEquals("Phone number must be provided", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when phone has invalid format")
    void shouldThrowExceptionWhenPhoneHasInvalidFormat() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new Phone("11987654321"));
        assertThrows(InvalidDocumentException.class, () -> new Phone("1234567890"));
        assertThrows(InvalidDocumentException.class, () -> new Phone("abc-defg-hijk"));

    }

    @Test
    @DisplayName("Should throw exception when phone has incomplete area code")
    void shouldThrowExceptionWhenPhoneHasIncompleteAreaCode() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> new Phone("(1) 98765-4321"));

        assertTrue(exception.getMessage().contains("Invalid phone number"));
        
    }

    @Test
    @DisplayName("Should throw exception when phone has missing hyphen")
    void shouldThrowExceptionWhenPhoneHasMissingHyphen() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new Phone("(11) 987654321"));

    }

    @Test
    @DisplayName("Should throw exception when phone has wrong digit count")
    void shouldThrowExceptionWhenPhoneHasWrongDigitCount() {

        // Act & Assert
        assertThrows(InvalidDocumentException.class, () -> new Phone("(11) 9876-4321")); // Missing digit
        assertThrows(InvalidDocumentException.class, () -> new Phone("(11) 987654-4321")); // Extra digit

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        Phone phone = new Phone("(11) 98765-4321");

        // Assert - @Value from Lombok makes it immutable
        assertNotNull(phone.getNumber());
        // No setter methods should exist
        assertThrows(NoSuchMethodException.class, () -> 
            phone.getClass().getMethod("setNumber", String.class)
        );

    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {

        // Arrange
        Phone phone1 = new Phone("(11) 98765-4321");
        Phone phone2 = new Phone("(11) 98765-4321");
        Phone phone3 = new Phone("(21) 98765-4321");

        // Assert
        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
        assertEquals(phone1.hashCode(), phone2.hashCode());

    }

    @Test
    @DisplayName("Should accept different valid area codes")
    void shouldAcceptDifferentValidAreaCodes() {

        // Act & Assert
        assertDoesNotThrow(() -> new Phone("(11) 98765-4321")); // São Paulo
        assertDoesNotThrow(() -> new Phone("(21) 98765-4321")); // Rio de Janeiro
        assertDoesNotThrow(() -> new Phone("(85) 98765-4321")); // Ceará
        assertDoesNotThrow(() -> new Phone("(61) 98765-4321")); // Brasília

    }

    @Test
    @DisplayName("Should accept landline with 8 digits")
    void shouldAcceptLandlineWith8Digits() {

        // Act & Assert
        assertDoesNotThrow(() -> new Phone("(11) 3456-7890"));
        assertDoesNotThrow(() -> new Phone("(21) 2345-6789"));

    }

    @Test
    @DisplayName("Should accept mobile with 9 digits")
    void shouldAcceptMobileWith9Digits() {

        // Act & Assert
        assertDoesNotThrow(() -> new Phone("(11) 98765-4321"));
        assertDoesNotThrow(() -> new Phone("(21) 99876-5432"));

    }

    @Test
    @DisplayName("Should accept international format with different country codes")
    void shouldAcceptInternationalFormatWithDifferentCountryCodes() {

        // Act & Assert
        assertDoesNotThrow(() -> new Phone("+55 11 98765-4321")); // Brazil
        assertDoesNotThrow(() -> new Phone("+1 11 98765-4321")); // US/Canada
        assertDoesNotThrow(() -> new Phone("+351 11 98765-4321")); // Portugal
        
    }
}
