package com.example.collab.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;

@DisplayName("WorkTimeMapper Tests")
class WorkTimeMapperTest {

    private final WorkTimeMapper workTimeMapper = Mappers.getMapper(WorkTimeMapper.class);

    @Test
    @DisplayName("Should map request dto to entity")
    void shouldMapRequestDtoToEntity() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
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

        WorkTime result = workTimeMapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Commercial", result.getDescription());
        assertTrue(result.getIsActive());
        assertEquals(LocalTime.of(8, 0), result.getInitialTime());
        assertEquals(LocalTime.of(17, 0), result.getEndTime());
        assertFalse(result.getIsOvernight());
        assertEquals(LocalTime.of(12, 0), result.getInitialBreakTime());
        assertEquals(LocalTime.of(13, 0), result.getEndBreakTime());
        assertTrue(result.getRequiresPunch());
        assertFalse(result.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should map request dto for overnight shift to entity")
    void shouldMapRequestDtoForOvernightShiftToEntity() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
            "Night Shift",
            false,
            LocalTime.of(22, 0),
            LocalTime.of(6, 0),
            true,
            LocalTime.of(2, 0),
            LocalTime.of(3, 0),
            false,
            true
        );

        WorkTime result = workTimeMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Night Shift", result.getDescription());
        assertFalse(result.getIsActive());
        assertEquals(LocalTime.of(22, 0), result.getInitialTime());
        assertEquals(LocalTime.of(6, 0), result.getEndTime());
        assertTrue(result.getIsOvernight());
        assertEquals(LocalTime.of(2, 0), result.getInitialBreakTime());
        assertEquals(LocalTime.of(3, 0), result.getEndBreakTime());
        assertFalse(result.getRequiresPunch());
        assertTrue(result.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should map entity to response dto")
    void shouldMapEntityToResponseDto() {
        WorkTime entity = new WorkTime(1L);
        entity.setIsActive(true);
        entity.setInitialTime(LocalTime.of(8, 0));
        entity.setEndTime(LocalTime.of(17, 0));
        entity.setIsOvernight(false);
        entity.setInitialBreakTime(LocalTime.of(12, 0));
        entity.setEndBreakTime(LocalTime.of(13, 0));
        entity.setRequiresPunch(true);
        entity.setAutoGeneratePunches(false);
        entity.setDescription("Commercial");

        WorkTimeResponseDTO result = workTimeMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("Commercial", result.description());
        assertTrue(result.isActive());
        assertEquals(LocalTime.of(8, 0), result.initialTime());
        assertEquals(LocalTime.of(17, 0), result.endTime());
        assertFalse(result.isOvernight());
        assertEquals(LocalTime.of(12, 0), result.initialBreakTime());
        assertEquals(LocalTime.of(13, 0), result.endBreakTime());
        assertTrue(result.requiresPunch());
        assertFalse(result.autoGeneratePunches());
    }

    @Test
    @DisplayName("Should map entity with overnight flag to response dto")
    void shouldMapEntityWithOvernightFlagToResponseDto() {
        WorkTime entity = new WorkTime(2L);
        entity.setDescription("Night Shift");
        entity.setIsActive(false);
        entity.setInitialTime(LocalTime.of(22, 0));
        entity.setEndTime(LocalTime.of(6, 0));
        entity.setIsOvernight(true);
        entity.setInitialBreakTime(LocalTime.of(2, 0));
        entity.setEndBreakTime(LocalTime.of(3, 0));
        entity.setRequiresPunch(false);
        entity.setAutoGeneratePunches(true);

        WorkTimeResponseDTO result = workTimeMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("Night Shift", result.description());
        assertFalse(result.isActive());
        assertTrue(result.isOvernight());
        assertTrue(result.autoGeneratePunches());
        assertFalse(result.requiresPunch());
    }

    @Test
    @DisplayName("Should update entity from another work time")
    void shouldUpdateEntityFromAnotherWorkTime() {
        WorkTime entity = new WorkTime(1L);
        entity.setDescription("Commercial");
        entity.setIsActive(true);
        entity.setInitialTime(LocalTime.of(8, 0));
        entity.setEndTime(LocalTime.of(17, 0));
        entity.setIsOvernight(false);
        entity.setInitialBreakTime(LocalTime.of(12, 0));
        entity.setEndBreakTime(LocalTime.of(13, 0));
        entity.setRequiresPunch(true);
        entity.setAutoGeneratePunches(false);

        WorkTime updatedObject = new WorkTime(2L);
        updatedObject.setDescription("Flexible");
        updatedObject.setIsActive(false);
        updatedObject.setInitialTime(LocalTime.of(9, 0));
        updatedObject.setEndTime(LocalTime.of(18, 0));
        updatedObject.setIsOvernight(false);
        updatedObject.setInitialBreakTime(LocalTime.of(13, 0));
        updatedObject.setEndBreakTime(LocalTime.of(14, 0));
        updatedObject.setRequiresPunch(false);
        updatedObject.setAutoGeneratePunches(true);

        workTimeMapper.updateEntity(entity, updatedObject);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Flexible", entity.getDescription());
        assertFalse(entity.getIsActive());
        assertEquals(LocalTime.of(9, 0), entity.getInitialTime());
        assertEquals(LocalTime.of(18, 0), entity.getEndTime());
        assertFalse(entity.getIsOvernight());
        assertEquals(LocalTime.of(13, 0), entity.getInitialBreakTime());
        assertEquals(LocalTime.of(14, 0), entity.getEndBreakTime());
        assertFalse(entity.getRequiresPunch());
        assertTrue(entity.getAutoGeneratePunches());
    }

    @Test
    @DisplayName("Should preserve entity id during update")
    void shouldPreserveEntityIdDuringUpdate() {
        WorkTime entity = new WorkTime(5L);
        entity.setDescription("Original");
        entity.setInitialTime(LocalTime.of(8, 0));

        WorkTime updatedObject = new WorkTime(10L);
        updatedObject.setDescription("Updated");
        updatedObject.setInitialTime(LocalTime.of(9, 0));

        workTimeMapper.updateEntity(entity, updatedObject);

        assertEquals(5L, entity.getId());
        assertEquals("Updated", entity.getDescription());
        assertEquals(LocalTime.of(9, 0), entity.getInitialTime());
    }

    @Test
    @DisplayName("Should map entity with minimal information to response dto")
    void shouldMapEntityWithMinimalInformationToResponseDto() {
        WorkTime entity = new WorkTime(3L);
        entity.setDescription("Minimal");

        WorkTimeResponseDTO result = workTimeMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("Minimal", result.description());
    }

    @Test
    @DisplayName("Should handle null break times in mapping")
    void shouldHandleNullBreakTimesInMapping() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
            "No Break",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(17, 0),
            false,
            null,
            null,
            true,
            false
        );

        WorkTime result = workTimeMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("No Break", result.getDescription());
        assertNull(result.getInitialBreakTime());
        assertNull(result.getEndBreakTime());
    }

    @Test
    @DisplayName("Should handle edge case times in mapping")
    void shouldHandleEdgeCaseTimesInMapping() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
            "Edge Case",
            true,
            LocalTime.of(0, 0),
            LocalTime.of(23, 59),
            false,
            LocalTime.of(11, 30),
            LocalTime.of(12, 30),
            true,
            false
        );

        WorkTime result = workTimeMapper.toEntity(request);

        assertNotNull(result);
        assertEquals(LocalTime.of(0, 0), result.getInitialTime());
        assertEquals(LocalTime.of(23, 59), result.getEndTime());
    }
}
