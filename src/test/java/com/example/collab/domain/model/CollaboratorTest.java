package com.example.collab.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.collab.domain.valueobject.contact.Email;
import com.example.collab.domain.valueobject.contact.Phone;

@DisplayName("Collaborator Entity Tests")
class CollaboratorTest {

    @Test
    @DisplayName("Should create collaborator with basic fields")
    void shouldCreateCollaboratorWithBasicFields() {
        Collaborator collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setBirthDate(LocalDate.of(1990, 5, 15));
        collaborator.setManager(true);
        collaborator.setSupportManager(false);

        assertEquals("John Doe", collaborator.getName());
        assertTrue(collaborator.getManager());
        assertFalse(collaborator.getSupportManager());
    }

    @Test
    @DisplayName("Should set contact value objects")
    void shouldSetContactValueObjects() {
        Collaborator collaborator = new Collaborator();
        collaborator.setEmail(new Email("john.doe@example.com"));
        collaborator.setPhone(new Phone("(11) 98765-4321"));

        assertEquals("john.doe@example.com", collaborator.getEmail().getEmail());
        assertNotNull(collaborator.getPhone());
    }
}
