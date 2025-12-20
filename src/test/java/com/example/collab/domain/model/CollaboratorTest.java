package com.example.collab.domain.model;

import com.example.collab.domain.valueobject.contact.Email;
import com.example.collab.domain.valueobject.contact.Phone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DisplayName("Collaborator Entity Tests")
class CollaboratorTest {

    @Test
    @DisplayName("Should create Collaborator with personal information")
    void shouldCreateCollaboratorWithPersonalInformation() {
        // Arrange & Act
        Collaborator collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setBirthDate(LocalDate.of(1990, 5, 15));
        collaborator.setMaritalStatus("Single");
        collaborator.setNationality("Brazilian");

        // Assert
        assertNotNull(collaborator);
        assertEquals("John Doe", collaborator.getName());
        assertEquals(LocalDate.of(1990, 5, 15), collaborator.getBirthDate());
        assertEquals("Single", collaborator.getMaritalStatus());
        assertEquals("Brazilian", collaborator.getNationality());
    }

    @Test
    @DisplayName("Should create Collaborator with contact information")
    void shouldCreateCollaboratorWithContactInformation() {
        // Arrange
        Collaborator collaborator = new Collaborator();
        Email email = new Email("john.doe@example.com");
        Phone phone = new Phone("+55 16 9992-1821");

        // Act
        collaborator.setEmail(email);
        collaborator.setPhone(phone);
        collaborator.setAddress("123 Main St, São Paulo, SP");

        // Assert
        assertNotNull(collaborator.getEmail());
        assertNotNull(collaborator.getPhone());
        assertEquals("123 Main St, São Paulo, SP", collaborator.getAddress());
    }

    @Test
    @DisplayName("Should create Collaborator with professional information")
    void shouldCreateCollaboratorWithProfessionalInformation() {
        // Arrange
        Collaborator collaborator = new Collaborator();
        Department department = new Department();
        department.setNumber(101);
        department.setName("Engineering");

        // Act
        collaborator.setPosition("Software Engineer");
        collaborator.setDepartment(department);
        collaborator.setAdmissionDate(LocalDate.of(2024, 1, 1));
        collaborator.setContractType("CLT");
        collaborator.setSalary(5000.0);
        collaborator.setRegistration(12345);
        collaborator.setWorkload("40h/week");

        // Assert
        assertEquals("Software Engineer", collaborator.getPosition());
        assertEquals(department, collaborator.getDepartment());
        assertEquals(LocalDate.of(2024, 1, 1), collaborator.getAdmissionDate());
        assertEquals("CLT", collaborator.getContractType());
        assertEquals(5000.0, collaborator.getSalary());
        assertEquals(12345, collaborator.getRegistration());
        assertEquals("40h/week", collaborator.getWorkload());
    }

    @Test
    @DisplayName("Should set collaborator as manager")
    void shouldSetCollaboratorAsManager() {
        // Arrange
        Collaborator collaborator = new Collaborator();

        // Act
        collaborator.setManager(true);

        // Assert
        assertTrue(collaborator.isManager());
    }

    @Test
    @DisplayName("Should set collaborator as support manager")
    void shouldSetCollaboratorAsSupportManager() {
        // Arrange
        Collaborator collaborator = new Collaborator();

        // Act
        collaborator.setSupportManager(true);

        // Assert
        assertTrue(collaborator.isSupportManager());
    }

    @Test
    @DisplayName("Should create collaborator as both manager and support manager")
    void shouldCreateCollaboratorAsBothManagerAndSupportManager() {
        // Arrange
        Collaborator collaborator = new Collaborator();

        // Act
        collaborator.setManager(true);
        collaborator.setSupportManager(true);

        // Assert
        assertTrue(collaborator.isManager());
        assertTrue(collaborator.isSupportManager());
    }

    @Test
    @DisplayName("Should update collaborator salary")
    void shouldUpdateCollaboratorSalary() {
        // Arrange
        Collaborator collaborator = new Collaborator();
        collaborator.setSalary(5000.0);

        // Act
        collaborator.setSalary(6000.0);

        // Assert
        assertEquals(6000.0, collaborator.getSalary());
    }

    @Test
    @DisplayName("Should update collaborator department")
    void shouldUpdateCollaboratorDepartment() {
        // Arrange
        Collaborator collaborator = new Collaborator();
        Department oldDepartment = new Department();
        oldDepartment.setNumber(101);
        oldDepartment.setName("IT");
        collaborator.setDepartment(oldDepartment);

        Department newDepartment = new Department();
        newDepartment.setNumber(102);
        newDepartment.setName("HR");

        // Act
        collaborator.setDepartment(newDepartment);

        // Assert
        assertEquals(newDepartment, collaborator.getDepartment());
        assertEquals("HR", collaborator.getDepartment().getName());
    }

    @Test
    @DisplayName("Should create empty Collaborator using NoArgsConstructor")
    void shouldCreateEmptyCollaboratorUsingNoArgsConstructor() {
        // Act
        Collaborator collaborator = new Collaborator();

        // Assert
        assertNotNull(collaborator);
        assertNull(collaborator.getId());
        assertNull(collaborator.getName());
        assertNull(collaborator.getBirthDate());
        assertFalse(collaborator.isManager());
        assertFalse(collaborator.isSupportManager());
    }

    @Test
    @DisplayName("Should handle null department")
    void shouldHandleNullDepartment() {
        // Arrange
        Collaborator collaborator = new Collaborator();

        // Act
        collaborator.setDepartment(null);

        // Assert
        assertNull(collaborator.getDepartment());
    }

    @Test
    @DisplayName("Should set registration number")
    void shouldSetRegistrationNumber() {
        // Arrange
        Collaborator collaborator = new Collaborator();
        Integer registration = 99999;

        // Act
        collaborator.setRegistration(registration);

        // Assert
        assertEquals(registration, collaborator.getRegistration());
    }
}
