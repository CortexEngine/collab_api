package com.example.collab.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.collab.domain.model.ScheduleRotation;
import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;

@DisplayName("ScheduleRotationMapper Tests")
class ScheduleRotationMapperTest {

    private final ScheduleRotationMapper scheduleRotationMapper = Mappers.getMapper(ScheduleRotationMapper.class);

    @Test
    @DisplayName("Should map request dto to entity")
    void shouldMapRequestDtoToEntity() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            1L,
            2L,
            List.of(1, 2, 3, 4, 5),
            List.of(true, true, true, true, true)
        );

        ScheduleRotation result = scheduleRotationMapper.toEntity(request);

        assertNotNull(result);
        assertNotNull(result.getWorkSchedule());
        assertNotNull(result.getWorkTime());
        assertEquals(1L, result.getWorkSchedule().getId());
        assertEquals(2L, result.getWorkTime().getId());
        assertEquals(5, result.getDayIndexs().size());
        assertEquals(5, result.getWorkdays().size());
    }

    @Test
    @DisplayName("Should map request dto with null work schedule")
    void shouldMapRequestDtoWithNullWorkSchedule() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            null,
            2L,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        ScheduleRotation result = scheduleRotationMapper.toEntity(request);

        assertNotNull(result);
        assertNull(result.getWorkSchedule());
        assertNotNull(result.getWorkTime());
        assertEquals(2L, result.getWorkTime().getId());
    }

    @Test
    @DisplayName("Should map request dto with null work time")
    void shouldMapRequestDtoWithNullWorkTime() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            1L,
            null,
            List.of(1, 2, 3),
            List.of(true, true, true)
        );

        ScheduleRotation result = scheduleRotationMapper.toEntity(request);

        assertNotNull(result);
        assertNotNull(result.getWorkSchedule());
        assertEquals(1L, result.getWorkSchedule().getId());
        assertNull(result.getWorkTime());
    }

    @Test
    @DisplayName("Should update entity from request dto")
    void shouldUpdateEntityFromRequestDto() {
        ScheduleRotation entity = new ScheduleRotation();
        entity.setWorkSchedule(new WorkSchedule(1L));
        entity.setWorkTime(new WorkTime(1L));
        entity.setDayIndexs(new ArrayList<>(List.of(1, 2)));
        entity.setWorkdays(new ArrayList<>(List.of(true, false)));

        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            2L,
            3L,
            List.of(1, 2, 3, 4),
            List.of(true, true, true, true)
        );

        scheduleRotationMapper.updateEntity(entity, request);

        assertNotNull(entity);
        assertEquals(2L, entity.getWorkSchedule().getId());
        assertEquals(3L, entity.getWorkTime().getId());
        assertEquals(4, entity.getDayIndexs().size());
        assertEquals(4, entity.getWorkdays().size());
    }

    @Test
    @DisplayName("Should map entity to response dto")
    void shouldMapEntityToResponseDto() {
        ScheduleRotation entity = new ScheduleRotation();
        entity.setWorkSchedule(new WorkSchedule(1L));
        entity.setWorkTime(new WorkTime(2L));
        entity.setDayIndexs(List.of(1, 2, 3));
        entity.setWorkdays(List.of(true, true, false));

        ScheduleRotationResponseDTO result = scheduleRotationMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(1, result.workSchedule());
        assertEquals(2, result.workTime());
        assertEquals(3, result.dayIndexs().size());
        assertEquals(3, result.workdays().size());
    }

    @Test
    @DisplayName("Should map entity with null work schedule to response dto")
    void shouldMapEntityWithNullWorkScheduleToResponseDto() {
        ScheduleRotation entity = new ScheduleRotation();
        entity.setWorkSchedule(null);
        entity.setWorkTime(new WorkTime(2L));
        entity.setDayIndexs(List.of(1, 2));
        entity.setWorkdays(List.of(true, false));

        ScheduleRotationResponseDTO result = scheduleRotationMapper.toResponse(entity);

        assertNotNull(result);
        assertNull(result.workSchedule());
        assertEquals(2, result.workTime());
        assertEquals(2, result.dayIndexs().size());
    }

    @Test
    @DisplayName("Should map entity with null work time to response dto")
    void shouldMapEntityWithNullWorkTimeToResponseDto() {
        ScheduleRotation entity = new ScheduleRotation();
        entity.setWorkSchedule(new WorkSchedule(1L));
        entity.setWorkTime(null);
        entity.setDayIndexs(List.of(1, 2, 3));
        entity.setWorkdays(List.of(true, true, true));

        ScheduleRotationResponseDTO result = scheduleRotationMapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(1, result.workSchedule());
        assertNull(result.workTime());
        assertEquals(3, result.dayIndexs().size());
    }

    @Test
    @DisplayName("Should convert work schedule id to work schedule object")
    void shouldConvertWorkScheduleIdToWorkScheduleObject() {
        WorkSchedule result = scheduleRotationMapper.toWorkSchedule(5L);

        assertNotNull(result);
        assertEquals(5L, result.getId());
    }

    @Test
    @DisplayName("Should return null when converting null work schedule id")
    void shouldReturnNullWhenConvertingNullWorkScheduleId() {
        WorkSchedule result = scheduleRotationMapper.toWorkSchedule(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should convert work schedule object to id")
    void shouldConvertWorkScheduleObjectToId() {
        WorkSchedule workSchedule = new WorkSchedule(10L);

        Long result = scheduleRotationMapper.fromWorkSchedule(workSchedule);

        assertNotNull(result);
        assertEquals(10L, result);
    }

    @Test
    @DisplayName("Should return null when converting null work schedule object")
    void shouldReturnNullWhenConvertingNullWorkScheduleObject() {
        Long result = scheduleRotationMapper.fromWorkSchedule(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should convert work time id to work time object")
    void shouldConvertWorkTimeIdToWorkTimeObject() {
        WorkTime result = scheduleRotationMapper.toWorkTime(7L);

        assertNotNull(result);
        assertEquals(7L, result.getId());
    }

    @Test
    @DisplayName("Should return null when converting null work time id")
    void shouldReturnNullWhenConvertingNullWorkTimeId() {
        WorkTime result = scheduleRotationMapper.toWorkTime(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should convert work time object to id")
    void shouldConvertWorkTimeObjectToId() {
        WorkTime workTime = new WorkTime(12L);

        Long result = scheduleRotationMapper.fromWorkTime(workTime);

        assertNotNull(result);
        assertEquals(12L, result);
    }

    @Test
    @DisplayName("Should return null when converting null work time object")
    void shouldReturnNullWhenConvertingNullWorkTimeObject() {
        Long result = scheduleRotationMapper.fromWorkTime(null);

        assertNull(result);
    }
}
