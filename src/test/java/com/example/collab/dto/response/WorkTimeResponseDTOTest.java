package com.example.collab.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WorkTimeResponseDTO Tests")
class WorkTimeResponseDTOTest {

    @Test
    @DisplayName("Should create valid WorkTimeResponseDTO")
    void shouldCreateValidWorkTimeResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertEquals("Commercial", dto.description());
        assertTrue(dto.isActive());
        assertEquals(LocalTime.of(8, 0), dto.initialTime());
        assertEquals(LocalTime.of(17, 0), dto.endTime());
        assertFalse(dto.isOvernight());
        assertEquals(LocalTime.of(12, 0), dto.initialBreakTime());
        assertEquals(LocalTime.of(13, 0), dto.endBreakTime());
        assertTrue(dto.requiresPunch());
        assertFalse(dto.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should allow null description in response DTO")
    void shouldAllowNullDescriptionInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            null,
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.description());
        assertTrue(dto.isActive());
    }

    @Test
    @DisplayName("Should allow null isActive in response DTO")
    void shouldAllowNullIsActiveInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            null,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.isActive());
    }

    @Test
    @DisplayName("Should allow null initialTime in response DTO")
    void shouldAllowNullInitialTimeInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            null,
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.initialTime());
    }

    @Test
    @DisplayName("Should allow null endTime in response DTO")
    void shouldAllowNullEndTimeInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            null,
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.endTime());
    }

    @Test
    @DisplayName("Should allow null isOvernight in response DTO")
    void shouldAllowNullIsOvernightInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            null,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.isOvernight());
    }

    @Test
    @DisplayName("Should allow null break times in response DTO")
    void shouldAllowNullBreakTimesInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            null,
            null,
            true,
            false
        );

        assertNotNull(dto);
        assertNull(dto.initialBreakTime());
        assertNull(dto.endBreakTime());
    }

    @Test
    @DisplayName("Should allow null requiresPunch in response DTO")
    void shouldAllowNullRequiresPunchInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            null,
            false
        );

        assertNotNull(dto);
        assertNull(dto.requiresPunch());
    }

    @Test
    @DisplayName("Should allow null autoGeneratePunches in response DTO")
    void shouldAllowNullAutoGeneratePunchesInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            null
        );

        assertNotNull(dto);
        assertNull(dto.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should allow all null fields in response DTO")
    void shouldAllowAllNullFieldsInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertNotNull(dto);
        assertNull(dto.description());
        assertNull(dto.isActive());
        assertNull(dto.initialTime());
        assertNull(dto.endTime());
        assertNull(dto.isOvernight());
        assertNull(dto.initialBreakTime());
        assertNull(dto.endBreakTime());
        assertNull(dto.requiresPunch());
        assertNull(dto.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should test equality between two identical response DTOs")
    void shouldTestEqualityBetweenIdenticalResponseDTOs() {
        LocalTime initialTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        LocalTime breakStart = LocalTime.of(12, 0);
        LocalTime breakEnd = LocalTime.of(13, 0);

        WorkTimeResponseDTO dto1 = new WorkTimeResponseDTO(
            "Commercial",
            true,
            initialTime,
            endTime,
            false,
            breakStart,
            breakEnd,
            true,
            false
        );

        WorkTimeResponseDTO dto2 = new WorkTimeResponseDTO(
            "Commercial",
            true,
            initialTime,
            endTime,
            false,
            breakStart,
            breakEnd,
            true,
            false
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different response DTOs")
    void shouldTestInequalityBetweenDifferentResponseDTOs() {
        WorkTimeResponseDTO dto1 = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        WorkTimeResponseDTO dto2 = new WorkTimeResponseDTO(
            "Night Shift",
            true,
            LocalTime.of(22, 0),
            LocalTime.of(6, 0),
            true,
            LocalTime.of(2, 0),
            LocalTime.of(3, 0),
            false,
            true
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation of response DTO")
    void shouldTestStringRepresentationOfResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("WorkTimeResponseDTO") || str.contains("Commercial"));
    }

    @Test
    @DisplayName("Should handle overnight shift in response DTO")
    void shouldHandleOvernightShiftInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Night Shift",
            true,
            LocalTime.of(22, 0),
            LocalTime.of(6, 0),
            true,
            LocalTime.of(2, 0),
            LocalTime.of(3, 0),
            false,
            true
        );

        assertNotNull(dto);
        assertEquals("Night Shift", dto.description());
        assertTrue(dto.isOvernight());
        assertEquals(LocalTime.of(22, 0), dto.initialTime());
        assertEquals(LocalTime.of(6, 0), dto.endTime());
        assertTrue(dto.autoGeneratePunches());
        assertFalse(dto.requiresPunch());
    }

    @Test
    @DisplayName("Should handle edge case times in response DTO")
    void shouldHandleEdgeCaseTimesInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Full Day",
            true,
            LocalTime.of(0, 0),
            LocalTime.of(23, 59),
            false,
            LocalTime.of(11, 30),
            LocalTime.of(12, 30),
            true,
            false
        );

        assertNotNull(dto);
        assertEquals(LocalTime.of(0, 0), dto.initialTime());
        assertEquals(LocalTime.of(23, 59), dto.endTime());
        assertEquals(LocalTime.of(11, 30), dto.initialBreakTime());
        assertEquals(LocalTime.of(12, 30), dto.endBreakTime());
    }

    @Test
    @DisplayName("Should test record field access")
    void shouldTestRecordFieldAccess() {
        String description = "Test Time";
        Boolean isActive = true;
        LocalTime initialTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        Boolean isOvernight = false;
        LocalTime initialBreakTime = LocalTime.of(12, 0);
        LocalTime endBreakTime = LocalTime.of(13, 0);
        Boolean requiresPunch = true;
        Boolean autoGeneratePunches = false;

        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            description,
            isActive,
            initialTime,
            endTime,
            isOvernight,
            initialBreakTime,
            endBreakTime,
            requiresPunch,
            autoGeneratePunches
        );

        assertEquals(description, dto.description());
        assertEquals(isActive, dto.isActive());
        assertEquals(initialTime, dto.initialTime());
        assertEquals(endTime, dto.endTime());
        assertEquals(isOvernight, dto.isOvernight());
        assertEquals(initialBreakTime, dto.initialBreakTime());
        assertEquals(endBreakTime, dto.endBreakTime());
        assertEquals(requiresPunch, dto.requiresPunch());
        assertEquals(autoGeneratePunches, dto.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should handle inactive work time in response DTO")
    void shouldHandleInactiveWorkTimeInResponseDTO() {
        WorkTimeResponseDTO dto = new WorkTimeResponseDTO(
            "Commercial",
            false,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            true,
            false
        );

        assertNotNull(dto);
        assertEquals("Commercial", dto.description());
        assertFalse(dto.isActive());
    }
}
