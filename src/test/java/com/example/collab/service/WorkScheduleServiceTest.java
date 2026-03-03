package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Should create work schedule")
    void shouldCreateWorkSchedule() {
        WorkScheduleRequestDTO request = new WorkScheduleRequestDTO("Scale 5x2", 5, 2, true);

        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleMapper.toEntity(request)).thenReturn(entity);
        when(workScheduleRepository.save(entity)).thenReturn(entity);
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.createWorkSchedule(request);

        assertEquals("Scale 5x2", result.description());
        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Should get active work schedule by id")
    void shouldGetActiveWorkScheduleById() {
        WorkSchedule entity = new WorkSchedule(1L);
        WorkScheduleResponseDTO response = new WorkScheduleResponseDTO("Scale 5x2", 5, 2, true);

        when(workScheduleRepository.findByIdAndIsActive(1L, true)).thenReturn(Optional.of(entity));
        when(workScheduleMapper.toResponse(entity)).thenReturn(response);

        WorkScheduleResponseDTO result = workScheduleService.getActiveWorkScheduleById(1L);

        assertEquals("Scale 5x2", result.description());
    }
}
