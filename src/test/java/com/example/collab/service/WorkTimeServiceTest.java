package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;
import com.example.collab.mapper.WorkTimeMapper;
import com.example.collab.repository.WorkTimeRepository;
import com.example.collab.service.validation.WorkTimeValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkTimeService Tests")
class WorkTimeServiceTest {

    @Mock
    private WorkTimeRepository workTimeRepository;

    @Mock
    private WorkTimeValidator workTimeValidator;

    @Mock
    private WorkTimeMapper workTimeMapper;

    @InjectMocks
    private WorkTimeService workTimeService;

    @Test
    @DisplayName("Should create work time successfully")
    void shouldCreateWorkTime() {
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

        WorkTime entity = new WorkTime(1L);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO(
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

        when(workTimeMapper.toEntity(request)).thenReturn(entity);
        when(workTimeRepository.save(entity)).thenReturn(entity);
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.createWorkTime(request);

        assertNotNull(result);
        assertEquals("Commercial", result.description());
        assertTrue(result.isActive());
        assertEquals(LocalTime.of(8, 0), result.initialTime());
        assertEquals(LocalTime.of(17, 0), result.endTime());
        verify(workTimeValidator).validateTimeEndBeforeInitialTime(request.initialTime(), request.endTime(), request.isOvernight());
        verify(workTimeValidator).validateSameWorkTimes(request.initialTime(), request.endTime(), request.initialBreakTime(), request.endBreakTime());
        verify(workTimeValidator).validateInitialAndEndWorkTimeLimit(request.initialTime(), request.endTime(), request.isOvernight());
        verify(workTimeValidator).validateInitialAndEndBreakTimeLimit(request.initialBreakTime(), request.endBreakTime(), request.isOvernight());
        verify(workTimeValidator).validateRequireAndAutoPunchSameTime(request.requiresPunch(), request.autoGeneratePunches());
        verify(workTimeRepository).save(entity);
    }

    @Test
    @DisplayName("Should get work time by id")
    void shouldGetWorkTimeById() {
        WorkTime entity = new WorkTime(1L);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO(
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

        when(workTimeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.getWorkTimeById(1L);

        assertNotNull(result);
        assertEquals("Commercial", result.description());
        verify(workTimeRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when work time not found by id")
    void shouldThrowExceptionWhenWorkTimeNotFoundById() {
        when(workTimeRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workTimeService.getWorkTimeById(1L)
        );

        assertEquals("Work time with id 1 not found.", exception.getMessage());
        verify(workTimeRepository).findById(1L);
    }

    @Test
    @DisplayName("Should get all work times")
    void shouldGetAllWorkTimes() {
        WorkTime entity1 = new WorkTime(1L);
        WorkTime entity2 = new WorkTime(2L);
        List<WorkTime> entities = Arrays.asList(entity1, entity2);

        WorkTimeResponseDTO response1 = new WorkTimeResponseDTO(
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
        WorkTimeResponseDTO response2 = new WorkTimeResponseDTO(
            "Night Shift", 
            true, 
            LocalTime.of(22, 0), 
            LocalTime.of(6, 0), 
            true, 
            LocalTime.of(2, 0), 
            LocalTime.of(3, 0), 
            true, 
            false
        );

        when(workTimeRepository.findAll()).thenReturn(entities);
        when(workTimeMapper.toResponse(entity1)).thenReturn(response1);
        when(workTimeMapper.toResponse(entity2)).thenReturn(response2);

        List<WorkTimeResponseDTO> result = workTimeService.getAllWorkTimes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workTimeRepository).findAll();
    }

    @Test
    @DisplayName("Should get active work times")
    void shouldGetActiveWorkTimes() {
        WorkTime entity1 = new WorkTime(1L);
        WorkTime entity2 = new WorkTime(2L);
        List<WorkTime> entities = Arrays.asList(entity1, entity2);

        WorkTimeResponseDTO response1 = new WorkTimeResponseDTO(
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
        WorkTimeResponseDTO response2 = new WorkTimeResponseDTO(
            "Night Shift", 
            true, 
            LocalTime.of(22, 0), 
            LocalTime.of(6, 0), 
            true, 
            LocalTime.of(2, 0), 
            LocalTime.of(3, 0), 
            true, 
            false
        );

        when(workTimeRepository.findByIsActive(true)).thenReturn(entities);
        when(workTimeMapper.toResponse(entity1)).thenReturn(response1);
        when(workTimeMapper.toResponse(entity2)).thenReturn(response2);

        List<WorkTimeResponseDTO> result = workTimeService.getActiveWorkTimes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(WorkTimeResponseDTO::isActive));
        verify(workTimeRepository).findByIsActive(true);
    }

    @Test
    @DisplayName("Should update work time successfully")
    void shouldUpdateWorkTime() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
            "Commercial Updated",
            true,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            false,
            LocalTime.of(12, 30),
            LocalTime.of(13, 30),
            true,
            true
        );

        WorkTime entity = new WorkTime(1L);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO(
            "Commercial Updated",
            true,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            false,
            LocalTime.of(12, 30),
            LocalTime.of(13, 30),
            true,
            true
        );

        when(workTimeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workTimeRepository.save(entity)).thenReturn(entity);
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.updateWorkTime(1L, request);

        assertNotNull(result);
        assertEquals("Commercial Updated", result.description());
        assertEquals(LocalTime.of(9, 0), result.initialTime());
        assertEquals(LocalTime.of(18, 0), result.endTime());
        verify(workTimeRepository).findById(1L);
        verify(workTimeValidator).validateIsActiveWorkTime(1L);
        verify(workTimeValidator).validateTimeEndBeforeInitialTime(request.initialTime(), request.endTime(), request.isOvernight());
        verify(workTimeValidator).validateSameWorkTimes(request.initialTime(), request.endTime(), request.initialBreakTime(), request.endBreakTime());
        verify(workTimeValidator).validateInitialAndEndWorkTimeLimit(request.initialTime(), request.endTime(), request.isOvernight());
        verify(workTimeValidator).validateInitialAndEndBreakTimeLimit(request.initialBreakTime(), request.endBreakTime(), request.isOvernight());
        verify(workTimeValidator).validateRequireAndAutoPunchSameTime(request.requiresPunch(), request.autoGeneratePunches());
        verify(workTimeRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent work time")
    void shouldThrowExceptionWhenUpdatingNonExistentWorkTime() {
        WorkTimeRequestDTO request = new WorkTimeRequestDTO(
            "Commercial Updated",
            true,
            LocalTime.of(9, 0),
            LocalTime.of(18, 0),
            false,
            LocalTime.of(12, 30),
            LocalTime.of(13, 30),
            true,
            true
        );

        when(workTimeRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workTimeService.updateWorkTime(1L, request)
        );

        assertEquals("Work time with id 1 not found.", exception.getMessage());
        verify(workTimeRepository).findById(1L);
        verify(workTimeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should activate work time successfully")
    void shouldActivateWorkTime() {
        WorkTime entity = new WorkTime(1L);
        entity.setIsActive(false);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO(
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

        when(workTimeRepository.findByIdAndIsActive(1L, false)).thenReturn(Optional.of(entity));
        when(workTimeRepository.save(entity)).thenReturn(entity);
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.activateWorkTime(1L);

        assertNotNull(result);
        assertTrue(result.isActive());
        verify(workTimeValidator).validateIsActiveWorkTime(1L);
        verify(workTimeRepository).findByIdAndIsActive(1L, false);
        verify(workTimeRepository).save(entity);
    }

    @Test
    @DisplayName("Should deactivate work time successfully")
    void shouldDeactivateWorkTime() {
        WorkTime entity = new WorkTime(1L);
        entity.setIsActive(true);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO(
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

        when(workTimeRepository.findByIdAndIsActive(1L, true)).thenReturn(Optional.of(entity));
        when(workTimeRepository.save(entity)).thenReturn(entity);
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.deactivateWorkTime(1L);

        assertNotNull(result);
        assertFalse(result.isActive());
        verify(workTimeValidator).validateIsActiveWorkTime(1L);
        verify(workTimeRepository).findByIdAndIsActive(1L, true);
        verify(workTimeRepository).save(entity);
    }
}
