package com.example.collab.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WorkScheduleResponseDTO Tests")
class WorkScheduleResponseDTOTest {

    @Test
    @DisplayName("Should create valid WorkScheduleResponseDTO")
    void shouldCreateValidWorkScheduleResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        assertNotNull(dto);
        assertEquals("Scale 5x2", dto.description());
        assertEquals(5, dto.workDaysPerWeek());
        assertEquals(2, dto.restDaysPerWeek());
        assertTrue(dto.isActive());
    }

    @Test
    @DisplayName("Should allow null description in response DTO")
    void shouldAllowNullDescriptionInResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            null,
            5,
            2,
            true
        );

        assertNotNull(dto);
        assertNull(dto.description());
        assertEquals(5, dto.workDaysPerWeek());
    }

    @Test
    @DisplayName("Should allow null workDaysPerWeek in response DTO")
    void shouldAllowNullWorkDaysPerWeekInResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            null,
            2,
            true
        );

        assertNotNull(dto);
        assertNull(dto.workDaysPerWeek());
        assertEquals(2, dto.restDaysPerWeek());
    }

    @Test
    @DisplayName("Should allow null restDaysPerWeek in response DTO")
    void shouldAllowNullRestDaysPerWeekInResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            null,
            true
        );

        assertNotNull(dto);
        assertEquals(5, dto.workDaysPerWeek());
        assertNull(dto.restDaysPerWeek());
    }

    @Test
    @DisplayName("Should allow null isActive in response DTO")
    void shouldAllowNullIsActiveInResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            null
        );

        assertNotNull(dto);
        assertNull(dto.isActive());
    }

    @Test
    @DisplayName("Should allow all null fields in response DTO")
    void shouldAllowAllNullFieldsInResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            null,
            null,
            null,
            null
        );

        assertNotNull(dto);
        assertNull(dto.description());
        assertNull(dto.workDaysPerWeek());
        assertNull(dto.restDaysPerWeek());
        assertNull(dto.isActive());
    }

    @Test
    @DisplayName("Should handle inactive status")
    void shouldHandleInactiveStatus() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            false
        );

        assertNotNull(dto);
        assertEquals("Scale 5x2", dto.description());
        assertFalse(dto.isActive());
    }

    @Test
    @DisplayName("Should test equality between two identical response DTOs")
    void shouldTestEqualityBetweenIdenticalResponseDTOs() {
        WorkScheduleResponseDTO dto1 = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        WorkScheduleResponseDTO dto2 = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different response DTOs")
    void shouldTestInequalityBetweenDifferentResponseDTOs() {
        WorkScheduleResponseDTO dto1 = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        WorkScheduleResponseDTO dto2 = new WorkScheduleResponseDTO(
            "Scale 6x1",
            6,
            1,
            true
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation of response DTO")
    void shouldTestStringRepresentationOfResponseDTO() {
        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("WorkScheduleResponseDTO") || str.contains("Scale 5x2"));
    }

    @Test
    @DisplayName("Should test record field access")
    void shouldTestRecordFieldAccess() {
        String description = "Test Schedule";
        Integer workDaysPerWeek = 4;
        Integer restDaysPerWeek = 3;
        Boolean isActive = true;

        WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
            description,
            workDaysPerWeek,
            restDaysPerWeek,
            isActive
        );

        assertEquals(description, dto.description());
        assertEquals(workDaysPerWeek, dto.workDaysPerWeek());
        assertEquals(restDaysPerWeek, dto.restDaysPerWeek());
        assertEquals(isActive, dto.isActive());
    }

    @Test
    @DisplayName("Should handle extreme week configurations")
    void shouldHandleExtremeWeekConfigurations() {
        WorkScheduleResponseDTO dto1 = new WorkScheduleResponseDTO(
            "All Work",
            7,
            0,
            true
        );

        WorkScheduleResponseDTO dto2 = new WorkScheduleResponseDTO(
            "All Rest",
            0,
            7,
            true
        );

        assertEquals(7, dto1.workDaysPerWeek());
        assertEquals(0, dto1.restDaysPerWeek());
        assertEquals(0, dto2.workDaysPerWeek());
        assertEquals(7, dto2.restDaysPerWeek());
    }

    @Test
    @DisplayName("Should test with various schedule descriptions")
    void shouldTestWithVariousScheduleDescriptions() {
        String[] descriptions = {
            "Simple",
            "5x2 Standard",
            "Scale 6 days work 1 day rest",
            "Flexible with overtime",
            "Night Shift 5x2"
        };

        for (String description : descriptions) {
            WorkScheduleResponseDTO dto = new WorkScheduleResponseDTO(
                description,
                5,
                2,
                true
            );

            assertEquals(description, dto.description());
        }
    }
}
