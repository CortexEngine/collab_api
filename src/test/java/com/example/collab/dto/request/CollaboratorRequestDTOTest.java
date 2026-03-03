package com.example.collab.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CollaboratorRequestDTO Tests")
class CollaboratorRequestDTOTest {

    @Test
    @DisplayName("Should create record with required fields")
    void shouldCreateRecordWithRequiredFields() {
        CollaboratorRequestDTO dto = new CollaboratorRequestDTO(
            "John Doe",
            LocalDate.of(1990, 5, 15),
            "Single",
            "Brazilian",
            "john.doe@example.com",
            "(11) 98765-4321",
            "Main Street",
            "Software Engineer",
            101,
            false,
            false,
            LocalDate.now().plusDays(1),
            "CLT",
            5000.0,
            12345,
            null,
            "001",
            "1234",
            "123456789012-3",
            "Checking",
            "john.doe@example.com",
            "12345678901",
            null,
            null,
            "32630500000",
            "12345678901",
            "046.797.650-38",
            "16.387.753-1",
            null,
            null,
            null,
            null,
            null
        );

        assertEquals("John Doe", dto.name());
        assertEquals(12345, dto.registration());
        assertFalse(dto.manager());
        assertFalse(dto.supportManager());
    }
}
