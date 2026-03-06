package com.example.collab.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DepartmentResponseDTO Tests")
class DepartmentResponseDTOTest {

    @Test
    @DisplayName("Should create response dto with all fields")
    void shouldCreateResponseDtoWithAllFields() {
        DepartmentResponseDTO dto = new DepartmentResponseDTO(
            "Engineering",
            101,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2025, 12, 31),
            1001,
            List.of(1002, 1003),
            List.of(2001, 2002, 2003)
        );

        assertEquals("Engineering", dto.name());
        assertEquals(101, dto.number());
        assertEquals(1001, dto.managerRegistration());
        assertEquals(2, dto.managerSupportRegistration().size());
        assertEquals(3, dto.teamMembersRegistration().size());
    }
}
