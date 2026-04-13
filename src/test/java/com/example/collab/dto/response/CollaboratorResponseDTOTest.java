package com.example.collab.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.collab.domain.valueobject.CollaboratorStatus;

@DisplayName("CollaboratorResponseDTO Tests")
class CollaboratorResponseDTOTest {

    @Test
    @DisplayName("Should create response dto with required fields")
    void shouldCreateResponseDtoWithRequiredFields() {
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO(
            1L,
            "John Doe",
            LocalDate.of(1990, 5, 15),
            "Single",
            "Brazilian",
            "john.doe@example.com",
            "+55 16 9992-1821",
            "Main Street",
            "Software Engineer",
            101,
            false,
            false,
            LocalDate.of(2024, 1, 1),
            "CLT",
            CollaboratorStatus.Active,
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

        assertEquals(1L, dto.id());
        assertEquals("John Doe", dto.name());
        assertEquals(12345, dto.registration());
        assertEquals("001", dto.bank());
        assertFalse(dto.manager());
        assertFalse(dto.supportManager());
    }
}
