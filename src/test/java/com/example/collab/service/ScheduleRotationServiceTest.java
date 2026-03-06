package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.ScheduleRotation;
import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;
import com.example.collab.mapper.ScheduleRotationMapper;
import com.example.collab.repository.ScheduleRotationRepository;
import com.example.collab.service.validation.ScheduleRotationValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleRotationService Tests")
class ScheduleRotationServiceTest {

    @Mock
    private ScheduleRotationRepository scheduleRotationRepository;

    @Mock
    private ScheduleRotationValidator scheduleRotationValidator;

    @Mock
    private ScheduleRotationMapper scheduleRotationMapper;

    @InjectMocks
    private ScheduleRotationService scheduleRotationService;

    @Test
    @DisplayName("Should create schedule rotation successfully")
    void shouldCreateScheduleRotation() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 3, 4, 5),
            List.of(true, true, true, true, true)
        );

        ScheduleRotation entity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 1, List.of(1, 2, 3, 4, 5), List.of(true, true, true, true, true));

        when(scheduleRotationMapper.toEntity(request)).thenReturn(entity);
        when(scheduleRotationRepository.save(entity)).thenReturn(entity);
        when(scheduleRotationMapper.toResponse(entity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.createScheduleRotation(request);

        assertNotNull(result);
        assertEquals(5, result.dayIndexs().size());
        assertEquals(5, result.workdays().size());
        verify(scheduleRotationValidator).validateDayIndexesAndWorkdaysSize(request.dayIndexs(), request.workdays());
        verify(scheduleRotationValidator).validateMaxDayIndexPerSchedule(request.workSchedule(), request.workdays());
        verify(scheduleRotationValidator).validateMinDayIndexPerSchedule(request.workSchedule(), request.workdays());
        verify(scheduleRotationValidator).validateTotalDaysMatchSchedule(request.workSchedule(), request.dayIndexs());
        verify(scheduleRotationValidator).validateDuplicateDayIndex(request.workSchedule(), request.dayIndexs());
        verify(scheduleRotationRepository).save(entity);
    }

    @Test
    @DisplayName("Should get schedule rotation by work schedule id")
    void shouldGetScheduleRotationByWorkScheduleId() {
        ScheduleRotation entity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 1, List.of(1), List.of(true));

        when(scheduleRotationRepository.findByWorkScheduleId(1L)).thenReturn(Optional.of(entity));
        when(scheduleRotationMapper.toResponse(entity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.getScheduleRotationByWorkScheduleId(1L);

        assertNotNull(result);
        assertEquals(1, result.workSchedule());
        verify(scheduleRotationRepository).findByWorkScheduleId(1L);
    }

    @Test
    @DisplayName("Should throw exception when schedule rotation not found by work schedule id")
    void shouldThrowExceptionWhenScheduleRotationNotFoundByWorkScheduleId() {
        when(scheduleRotationRepository.findByWorkScheduleId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> scheduleRotationService.getScheduleRotationByWorkScheduleId(1L)
        );

        assertEquals("Schedule rotation not found", exception.getMessage());
        verify(scheduleRotationRepository).findByWorkScheduleId(1L);
    }

    @Test
    @DisplayName("Should get schedule rotation by work schedule id and work time id")
    void shouldGetScheduleRotationByWorkScheduleIdAndWorkTimeId() {
        ScheduleRotation entity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 2, List.of(1, 2), List.of(true, false));

        when(scheduleRotationRepository.findByWorkScheduleIdAndWorkTimeId(1L, 2L)).thenReturn(Optional.of(entity));
        when(scheduleRotationMapper.toResponse(entity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.getScheduleRotationByWorkScheduleIdAndWorkTimeId(1L, 2L);

        assertNotNull(result);
        assertEquals(1, result.workSchedule());
        assertEquals(2, result.workTime());
        verify(scheduleRotationRepository).findByWorkScheduleIdAndWorkTimeId(1L, 2L);
    }

    @Test
    @DisplayName("Should throw exception when schedule rotation not found by work schedule id and work time id")
    void shouldThrowExceptionWhenScheduleRotationNotFoundByWorkScheduleIdAndWorkTimeId() {
        when(scheduleRotationRepository.findByWorkScheduleIdAndWorkTimeId(1L, 2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> scheduleRotationService.getScheduleRotationByWorkScheduleIdAndWorkTimeId(1L, 2L)
        );

        assertEquals("Schedule rotation not found", exception.getMessage());
        verify(scheduleRotationRepository).findByWorkScheduleIdAndWorkTimeId(1L, 2L);
    }

    @Test
    @DisplayName("Should get schedule rotation by work schedule id and day index")
    void shouldGetScheduleRotationByWorkScheduleAndDayIndexs() {
        ScheduleRotation entity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 1, List.of(1, 2, 3), List.of(true, true, true));

        when(scheduleRotationRepository.findByWorkScheduleIdAndDayIndexs(1L, 2)).thenReturn(Optional.of(entity));
        when(scheduleRotationMapper.toResponse(entity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.getScheduleRotationByWorkScheduleAndDayIndexs(1L, 2);

        assertNotNull(result);
        assertEquals(1, result.workSchedule());
        verify(scheduleRotationRepository).findByWorkScheduleIdAndDayIndexs(1L, 2);
    }

    @Test
    @DisplayName("Should throw exception when schedule rotation not found by work schedule id and day index")
    void shouldThrowExceptionWhenScheduleRotationNotFoundByWorkScheduleAndDayIndexs() {
        when(scheduleRotationRepository.findByWorkScheduleIdAndDayIndexs(1L, 2)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> scheduleRotationService.getScheduleRotationByWorkScheduleAndDayIndexs(1L, 2)
        );

        assertEquals("Schedule rotation not found", exception.getMessage());
        verify(scheduleRotationRepository).findByWorkScheduleIdAndDayIndexs(1L, 2);
    }

    @Test
    @DisplayName("Should update schedule rotation successfully")
    void shouldUpdateScheduleRotation() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 3),
            List.of(true, true, false)
        );

        ScheduleRotation existingEntity = new ScheduleRotation();
        ScheduleRotation updatedEntity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 1, List.of(1, 2, 3), List.of(true, true, false));

        when(scheduleRotationRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        doNothing().when(scheduleRotationMapper).updateEntity(existingEntity, request);
        when(scheduleRotationRepository.save(existingEntity)).thenReturn(updatedEntity);
        when(scheduleRotationMapper.toResponse(updatedEntity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.updateScheduleRotation(1L, request);

        assertNotNull(result);
        assertEquals(3, result.dayIndexs().size());
        verify(scheduleRotationRepository).findById(1L);
        verify(scheduleRotationValidator).validateDayIndexesAndWorkdaysSize(request.dayIndexs(), request.workdays());
        verify(scheduleRotationValidator).validateMaxDayIndexPerSchedule(request.workSchedule(), request.workdays());
        verify(scheduleRotationValidator).validateMinDayIndexPerSchedule(request.workSchedule(), request.workdays());
        verify(scheduleRotationValidator).validateTotalDaysMatchSchedule(request.workSchedule(), request.dayIndexs());
        verify(scheduleRotationValidator).validateDuplicateDayIndex(request.workSchedule(), request.dayIndexs());
        verify(scheduleRotationMapper).updateEntity(existingEntity, request);
        verify(scheduleRotationRepository).save(existingEntity);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent schedule rotation")
    void shouldThrowExceptionWhenUpdatingNonExistentScheduleRotation() {
        ScheduleRotationRequestDTO request = new ScheduleRotationRequestDTO(
            1L,
            1L,
            List.of(1, 2, 3),
            List.of(true, true, false)
        );

        when(scheduleRotationRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> scheduleRotationService.updateScheduleRotation(1L, request)
        );

        assertEquals("Schedule rotation not found", exception.getMessage());
        verify(scheduleRotationRepository).findById(1L);
        verify(scheduleRotationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete schedule rotation successfully")
    void shouldDeleteScheduleRotation() {
        ScheduleRotation existingEntity = new ScheduleRotation();

        when(scheduleRotationRepository.findByWorkScheduleId(1L)).thenReturn(Optional.of(existingEntity));
        doNothing().when(scheduleRotationRepository).delete(existingEntity);

        scheduleRotationService.deleteScheduleRotation(1L);

        verify(scheduleRotationRepository).findByWorkScheduleId(1L);
        verify(scheduleRotationRepository).delete(existingEntity);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent schedule rotation")
    void shouldThrowExceptionWhenDeletingNonExistentScheduleRotation() {
        when(scheduleRotationRepository.findByWorkScheduleId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> scheduleRotationService.deleteScheduleRotation(1L)
        );

        assertEquals("Schedule rotation not found", exception.getMessage());
        verify(scheduleRotationRepository).findByWorkScheduleId(1L);
        verify(scheduleRotationRepository, never()).delete(any());
    }
}
