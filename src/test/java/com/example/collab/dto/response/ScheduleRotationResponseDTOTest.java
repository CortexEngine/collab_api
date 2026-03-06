package com.example.collab.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ScheduleRotationResponseDTO Tests")
class ScheduleRotationResponseDTOTest {

    @Test
    @DisplayName("Should create valid ScheduleRotationResponseDTO")
    void shouldCreateValidScheduleRotationResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3, 4, 5),
            List.of(true, true, true, true, true)
        );

        assertNotNull(dto);
        assertEquals(1, dto.workSchedule());
        assertEquals(2, dto.workTime());
        assertEquals(5, dto.dayIndexs().size());
        assertEquals(5, dto.workdays().size());
    }

    @Test
    @DisplayName("Should allow null workSchedule in response DTO")
    void shouldAllowNullWorkScheduleInResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            null,
            2,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        assertNotNull(dto);
        assertNull(dto.workSchedule());
        assertEquals(2, dto.workTime());
    }

    @Test
    @DisplayName("Should allow null workTime in response DTO")
    void shouldAllowNullWorkTimeInResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            null,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        assertNotNull(dto);
        assertEquals(1, dto.workSchedule());
        assertNull(dto.workTime());
    }

    @Test
    @DisplayName("Should allow null dayIndexs in response DTO")
    void shouldAllowNullDayIndexsInResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            null,
            List.of(true, true, true)
        );

        assertNotNull(dto);
        assertNull(dto.dayIndexs());
        assertEquals(3, dto.workdays().size());
    }

    @Test
    @DisplayName("Should allow null workdays in response DTO")
    void shouldAllowNullWorkdaysInResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3),
            null
        );

        assertNotNull(dto);
        assertEquals(3, dto.dayIndexs().size());
        assertNull(dto.workdays());
    }

    @Test
    @DisplayName("Should allow all null fields in response DTO")
    void shouldAllowAllNullFieldsInResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            null,
            null,
            null,
            null
        );

        assertNotNull(dto);
        assertNull(dto.workSchedule());
        assertNull(dto.workTime());
        assertNull(dto.dayIndexs());
        assertNull(dto.workdays());
    }

    @Test
    @DisplayName("Should test equality between two identical response DTOs")
    void shouldTestEqualityBetweenIdenticalResponseDTOs() {
        ScheduleRotationResponseDTO dto1 = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        ScheduleRotationResponseDTO dto2 = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should test inequality between different response DTOs")
    void shouldTestInequalityBetweenDifferentResponseDTOs() {
        ScheduleRotationResponseDTO dto1 = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        ScheduleRotationResponseDTO dto2 = new ScheduleRotationResponseDTO(
            1,
            3,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test string representation of response DTO")
    void shouldTestStringRepresentationOfResponseDTO() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3),
            List.of(true, false, true)
        );

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("ScheduleRotationResponseDTO") || str.contains("1") || str.contains("2"));
    }

    @Test
    @DisplayName("Should handle empty day indexes list")
    void shouldHandleEmptyDayIndexesList() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(),
            List.of()
        );

        assertNotNull(dto);
        assertEquals(0, dto.dayIndexs().size());
        assertEquals(0, dto.workdays().size());
    }

    @Test
    @DisplayName("Should handle large day indexes")
    void shouldHandleLargeDayIndexes() {
        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            1,
            2,
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            List.of(true, false, true, false, true, false, true, false, true, false)
        );

        assertNotNull(dto);
        assertEquals(10, dto.dayIndexs().size());
        assertEquals(10, dto.workdays().size());
    }

    @Test
    @DisplayName("Should test record field access")
    void shouldTestRecordFieldAccess() {
        Integer workSchedule = 5;
        Integer workTime = 10;
        List<Integer> dayIndexs = List.of(1, 2, 3, 4, 5);
        List<Boolean> workdays = List.of(true, true, true, true, true);

        ScheduleRotationResponseDTO dto = new ScheduleRotationResponseDTO(
            workSchedule,
            workTime,
            dayIndexs,
            workdays
        );

        assertEquals(workSchedule, dto.workSchedule());
        assertEquals(workTime, dto.workTime());
        assertEquals(dayIndexs, dto.dayIndexs());
        assertEquals(workdays, dto.workdays());
    }
}
