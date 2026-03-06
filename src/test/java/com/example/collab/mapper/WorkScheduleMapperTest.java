package com.example.collab.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;

@DisplayName("WorkScheduleMapper Tests")
class WorkScheduleMapperTest {

    private final WorkScheduleMapper workScheduleMapper = Mappers.getMapper(WorkScheduleMapper.class);

    @Test
    @DisplayName("Should map request dto to entity")
    void shouldMapRequestDtoToEntity() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO(
            "Scale 5x2",
            5,
            2,
            true
        );

        WorkSchedule result = workScheduleMapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Scale 5x2", result.getDescription());
        assertEquals(5, result.getWorkDaysPerWeek());
        assertEquals(2, result.getRestDaysPerWeek());
        assertTrue(result.getIsActive());
    }

    @Test
    @DisplayName("Should map request dto with inactive status to entity")
    void shouldMapRequestDtoWithInactiveStatusToEntity() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO(
            "Scale 6x1",
            6,
            1,
            false
        );

        WorkSchedule result = workScheduleMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Scale 6x1", result.getDescription());
        assertEquals(6, result.getWorkDaysPerWeek());
        assertEquals(1, result.getRestDaysPerWeek());
        assertFalse(result.getIsActive());
    }

    @Test
    @DisplayName("Should map entity to response dto")
    void shouldMapEntityToResponseDto() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setDescription("Scale 5x2");
        entity.setWorkDaysPerWeek(5);
        entity.setRestDaysPerWeek(2);
        entity.setIsActive(true);

        WorkScheduleResponseDTO result = workScheduleMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("Scale 5x2", result.description());
        assertEquals(5, result.workDaysPerWeek());
        assertEquals(2, result.restDaysPerWeek());
        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Should map entity with inactive status to response dto")
    void shouldMapEntityWithInactiveStatusToResponseDto() {
        WorkSchedule entity = new WorkSchedule(2L);
        entity.setDescription("Scale 6x1");
        entity.setWorkDaysPerWeek(6);
        entity.setRestDaysPerWeek(1);
        entity.setIsActive(false);

        WorkScheduleResponseDTO result = workScheduleMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("Scale 6x1", result.description());
        assertEquals(6, result.workDaysPerWeek());
        assertEquals(1, result.restDaysPerWeek());
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Should update entity from another work schedule")
    void shouldUpdateEntityFromAnotherWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setDescription("Scale 5x2");
        entity.setWorkDaysPerWeek(5);
        entity.setRestDaysPerWeek(2);
        entity.setIsActive(true);

        WorkSchedule updatedObject = new WorkSchedule(2L);
        updatedObject.setDescription("Scale 4x3");
        updatedObject.setWorkDaysPerWeek(4);
        updatedObject.setRestDaysPerWeek(3);
        updatedObject.setIsActive(false);

        workScheduleMapper.updateEntity(entity, updatedObject);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Scale 4x3", entity.getDescription());
        assertEquals(4, entity.getWorkDaysPerWeek());
        assertEquals(3, entity.getRestDaysPerWeek());
        assertFalse(entity.getIsActive());
    }

    @Test
    @DisplayName("Should preserve entity id during update")
    void shouldPreserveEntityIdDuringUpdate() {
        WorkSchedule entity = new WorkSchedule(5L);
        entity.setDescription("Original");
        entity.setWorkDaysPerWeek(5);
        entity.setRestDaysPerWeek(2);

        WorkSchedule updatedObject = new WorkSchedule(10L);
        updatedObject.setDescription("Updated");
        updatedObject.setWorkDaysPerWeek(6);
        updatedObject.setRestDaysPerWeek(1);

        workScheduleMapper.updateEntity(entity, updatedObject);

        assertEquals(5L, entity.getId());
        assertEquals("Updated", entity.getDescription());
    }

    @Test
    @DisplayName("Should handle null values during mapping")
    void shouldHandleNullValuesDuringMapping() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO(
            "Scale Test",
            4,
            3,
            true
        );

        WorkSchedule result = workScheduleMapper.toEntity(request);

        assertNotNull(result);
        assertNotNull(result.getDescription());
        assertNotNull(result.getWorkDaysPerWeek());
        assertNotNull(result.getRestDaysPerWeek());
        assertNotNull(result.getIsActive());
    }
}
