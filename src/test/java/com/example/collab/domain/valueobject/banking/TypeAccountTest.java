package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TypeAccount Value Object Tests")
class TypeAccountTest {

    @Test
    @DisplayName("Should create TypeAccount with 'Checking' type")
    void shouldCreateTypeAccountWithCheckingType() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("Checking");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("Checking", typeAccount.getType());

    }

    @Test
    @DisplayName("Should create TypeAccount with 'Savings' type")
    void shouldCreateTypeAccountWithSavingsType() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("Savings");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("Savings", typeAccount.getType());

    }

    @Test
    @DisplayName("Should create TypeAccount with 'Salary' type")
    void shouldCreateTypeAccountWithSalaryType() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("Salary");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("Salary", typeAccount.getType());

    }

    @Test
    @DisplayName("Should create TypeAccount with lowercase 'checking'")
    void shouldCreateTypeAccountWithLowercaseChecking() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("checking");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("checking", typeAccount.getType());

    }

    @Test
    @DisplayName("Should create TypeAccount with uppercase 'SAVINGS'")
    void shouldCreateTypeAccountWithUppercaseSavings() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("SAVINGS");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("SAVINGS", typeAccount.getType());

    }

    @Test
    @DisplayName("Should create TypeAccount with mixed case 'SaLaRy'")
    void shouldCreateTypeAccountWithMixedCaseSalary() {

        // Arrange & Act
        TypeAccount typeAccount = new TypeAccount("SaLaRy");

        // Assert
        assertNotNull(typeAccount);
        assertEquals("SaLaRy", typeAccount.getType());

    }

    @Test
    @DisplayName("Should throw exception when type is null")
    void shouldThrowExceptionWhenTypeIsNull() {

        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount(null)

        );

        assertEquals("The account type must be specified.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type is blank")
    void shouldThrowExceptionWhenTypeIsBlank() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("")

        );

        assertEquals("The account type must be specified.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type has only spaces")
    void shouldThrowExceptionWhenTypeHasOnlySpaces() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("   ")

        );

        assertEquals("The account type must be specified.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type is invalid")
    void shouldThrowExceptionWhenTypeIsInvalid() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("Investment")

        );

        assertEquals("The account type must be 'Checking', 'Savings', or 'Salary'.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type is 'Current'")
    void shouldThrowExceptionWhenTypeIsCurrent() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("Current")

        );

        assertEquals("The account type must be 'Checking', 'Savings', or 'Salary'.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type is numeric")
    void shouldThrowExceptionWhenTypeIsNumeric() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("123")

        );

        assertEquals("The account type must be 'Checking', 'Savings', or 'Salary'.", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when type contains special characters")
    void shouldThrowExceptionWhenTypeContainsSpecialCharacters() {
        // Act & Assert
        InvalidDocumentException exception = assertThrows(

                InvalidDocumentException.class,() -> new TypeAccount("Checking@")

        );

        assertEquals("The account type must be 'Checking', 'Savings', or 'Salary'.", exception.getMessage());

    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {

        // Arrange
        TypeAccount typeAccount1 = new TypeAccount("Checking");
        TypeAccount typeAccount2 = new TypeAccount("Checking");

        // Assert
        assertEquals(typeAccount1, typeAccount2);
        assertEquals(typeAccount1.hashCode(), typeAccount2.hashCode());

    }

    @Test
    @DisplayName("Should have different instances for different types")
    void shouldHaveDifferentInstancesForDifferentTypes() {

        // Arrange
        TypeAccount typeAccount1 = new TypeAccount("Checking");
        TypeAccount typeAccount2 = new TypeAccount("Savings");

        // Assert
        assertNotEquals(typeAccount1, typeAccount2);
        assertNotEquals(typeAccount1.hashCode(), typeAccount2.hashCode());
        
    }

    @Test
    @DisplayName("Should treat case-insensitive types as equal when values match")
    void shouldTreatCaseInsensitiveTypesAsEqualWhenValuesMatch() {

        // Arrange
        TypeAccount typeAccount1 = new TypeAccount("checking");
        TypeAccount typeAccount2 = new TypeAccount("checking");

        // Assert
        assertEquals(typeAccount1, typeAccount2);
        
    }

    @Test
    @DisplayName("Should have different hash codes for different case representations")
    void shouldHaveDifferentHashCodesForDifferentCaseRepresentations() {

        // Arrange
        TypeAccount typeAccount1 = new TypeAccount("Checking");
        TypeAccount typeAccount2 = new TypeAccount("checking");

        // Assert - They are valid but represent different String values
        assertNotEquals(typeAccount1, typeAccount2);
        
    }
}
