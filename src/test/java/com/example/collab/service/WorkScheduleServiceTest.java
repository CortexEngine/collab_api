package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;
import com.example.collab.mapper.WorkScheduleMapper;
import com.example.collab.repository.WorkScheduleRepository;
import com.example.collab.service.validation.WorkScheduleValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkScheduleService Tests")
class WorkScheduleServiceTest {

    @Mock
    private WorkScheduleRepository workScheduleRepository;

    @Mock
    private WorkScheduleValidator workScheduleValidator;

    @Mock
    private WorkScheduleMapper workScheduleMapper;

    @InjectMocks
    private WorkScheduleService workScheduleService;

    @Test
    @DisplayName("Should create work schedule successfully")
    void shouldCreateWorkSchedule() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO("Scale 5x2", 5, 2, true);

        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleMapper.toEntity(request)).thenReturn(entity);
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.createWorkSchedule(request);

        assertNotNull(result);
        assertEquals("Scale 5x2", result.description());
        assertTrue(result.isActive());
        assertEquals(5, result.workDaysPerWeek());
        assertEquals(2, result.restDaysPerWeek());
        verify(workScheduleValidator).validateManyWorkDaysPerWeek(request.workDaysPerWeek());
        verify(workScheduleValidator).validateManyRestDaysPerWeek(request.restDaysPerWeek());
        verify(workScheduleValidator).validateManyDaysPerWeek(request.workDaysPerWeek(), request.restDaysPerWeek());
        verify(workScheduleValidator).validateDescriptionExists(request.description());
        verify(workScheduleRepository).save(entity);
    }

    @Test
    @DisplayName("Should get active work schedule by id")
    void shouldGetActiveWorkScheduleById() {
        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleRepository.findByIdAndIsActive(1L, true)).thenReturn(Optional.of(entity));
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.getActiveWorkScheduleById(1L);

        assertNotNull(result);
        assertEquals("Scale 5x2", result.description());
        verify(workScheduleValidator).validateIsActiveWorkSchedule(1L);
        verify(workScheduleRepository).findByIdAndIsActive(1L, true);
    }

    @Test
    @DisplayName("Should get all work schedules")
    void shouldGetAllWorkSchedule() {
        WorkSchedule entity1 = new WorkSchedule(1L);
        WorkSchedule entity2 = new WorkSchedule(2L);
        List<WorkSchedule> entities = Arrays.asList(entity1, entity2);

        WorkScheduleResponseDTO response1 = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);
        WorkScheduleResponseDTO response2 = new WorkScheduleResponseDTO("Scale 6x1", 6, 1, true);

        when(workScheduleRepository.findAll()).thenReturn(entities);
        when(workScheduleMapper.toResponse(entity1)).thenReturn(response1);
        when(workScheduleMapper.toResponse(entity2)).thenReturn(response2);

        List<WorkScheduleResponseDTO> result = workScheduleService.getAllWorkSchedule();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(workScheduleRepository).findAll();
    }

    @Test
    @DisplayName("Should get work schedule by description")
    void shouldGetWorkScheduleByDescription() {
        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleRepository.findByDescription("Scale 5x2")).thenReturn(Optional.of(entity));
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.getWorkScheduleByDescription("Scale 5x2");

        assertNotNull(result);
        assertEquals("Scale 5x2", result.description());
        verify(workScheduleValidator).validateDescriptionExists("Scale 5x2");
        verify(workScheduleRepository).findByDescription("Scale 5x2");
    }

    @Test
    @DisplayName("Should get all active work schedules")
    void shouldGetAllActiveWorkSchedule() {
        WorkSchedule entity1 = new WorkSchedule(1L);
        WorkSchedule entity2 = new WorkSchedule(2L);
        List<WorkSchedule> entities = Arrays.asList(entity1, entity2);

        WorkScheduleResponseDTO response1 = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);
        WorkScheduleResponseDTO response2 = new WorkScheduleResponseDTO("Scale 6x1", 6, 1, true);

        when(workScheduleRepository.findByIsActive(true)).thenReturn(entities);
        when(workScheduleMapper.toResponse(entity1)).thenReturn(response1);
        when(workScheduleMapper.toResponse(entity2)).thenReturn(response2);

        List<WorkScheduleResponseDTO> result = workScheduleService.getAllActiveWorkSchedule();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(WorkScheduleResponseDTO::isActive));
        verify(workScheduleRepository).findByIsActive(true);
    }

    @Test
    @DisplayName("Should get all deactivated work schedules")
    void shouldGetAllDeactivatedWorkSchedule() {
        WorkSchedule entity1 = new WorkSchedule(1L);
        WorkSchedule entity2 = new WorkSchedule(2L);
        List<WorkSchedule> entities = Arrays.asList(entity1, entity2);

        WorkScheduleResponseDTO response1 = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, false);
        WorkScheduleResponseDTO response2 = new WorkScheduleResponseDTO("Scale 6x1", 6, 1, false);

        when(workScheduleRepository.findByIsActive(false)).thenReturn(entities);
        when(workScheduleMapper.toResponse(entity1)).thenReturn(response1);
        when(workScheduleMapper.toResponse(entity2)).thenReturn(response2);

        List<WorkScheduleResponseDTO> result = workScheduleService.getAllDeactivatedWorkSchedule();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(WorkScheduleResponseDTO::isActive));
        verify(workScheduleRepository).findByIsActive(false);
    }

    @Test
    @DisplayName("Should deactivate work schedule")
    void shouldDeactivateWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setIsActive(true);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, false);

        when(workScheduleRepository.findByIdAndIsActive(1L, true)).thenReturn(Optional.of(entity));
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.deactivateWorkSchedule(1L);

        assertNotNull(result);
        assertFalse(result.isActive());
        verify(workScheduleValidator).validateIsActiveWorkSchedule(1L);
        verify(workScheduleRepository).findByIdAndIsActive(1L, true);
        verify(workScheduleRepository).save(entity);
    }

    @Test
    @DisplayName("Should activate work schedule")
    void shouldActivateWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setIsActive(false);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.activateWorkSchedule(1L);

        assertNotNull(result);
        assertTrue(result.isActive());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw exception when activating non-existent work schedule")
    void shouldThrowExceptionWhenActivatingNonExistentWorkSchedule() {
        when(workScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workScheduleService.activateWorkSchedule(1L)
        );

        assertEquals("The work schedule does not exist.", exception.getMessage());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should deactivate work schedule with deacticateWorkSchedule method")
    void shouldDeacticateWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setIsActive(true);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, false);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.deacticateWorkSchedule(1L);

        assertNotNull(result);
        assertFalse(result.isActive());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw exception when deactivating non-existent work schedule")
    void shouldThrowExceptionWhenDeactivatingNonExistentWorkSchedule() {
        when(workScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workScheduleService.deacticateWorkSchedule(1L)
        );

        assertEquals("The work schedule does not exist.", exception.getMessage());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update work schedule successfully")
    void shouldUpdateWorkSchedule() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO("Scale 6x1 Updated", 6, 1, true);
        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 6x1 Updated", 6, 1, true);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.updateWorkSchedule(1L, request);

        assertNotNull(result);
        assertEquals("Scale 6x1 Updated", result.description());
        assertEquals(6, result.workDaysPerWeek());
        assertEquals(1, result.restDaysPerWeek());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleValidator).validateIsActiveWorkSchedule(1L);
        verify(workScheduleValidator).validateManyWorkDaysPerWeek(request.workDaysPerWeek());
        verify(workScheduleValidator).validateManyRestDaysPerWeek(request.restDaysPerWeek());
        verify(workScheduleValidator).validateManyDaysPerWeek(request.workDaysPerWeek(), request.restDaysPerWeek());
        verify(workScheduleRepository).save(entity);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent work schedule")
    void shouldThrowExceptionWhenUpdatingNonExistentWorkSchedule() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO("Scale 6x1 Updated", 6, 1, true);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workScheduleService.updateWorkSchedule(1L, request)
        );

        assertEquals("Work schedule with id 1 not found.", exception.getMessage());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete inactive work schedule successfully")
    void shouldDeleteWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setIsActive(false);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(workScheduleRepository).deleteById(1L);

        workScheduleService.deleteWorkSchedule(1L);

        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting active work schedule")
    void shouldThrowExceptionWhenDeletingActiveWorkSchedule() {
        WorkSchedule entity = new WorkSchedule(1L);
        entity.setIsActive(true);

        when(workScheduleRepository.findById(1L)).thenReturn(Optional.of(entity));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> workScheduleService.deleteWorkSchedule(1L)
        );

        assertEquals("It's not possible to delete the work schedule. Disable it or verify that the number is correct.", exception.getMessage());
        verify(workScheduleRepository).findById(1L);
        verify(workScheduleRepository, never()).deleteById(any());
    }
}
