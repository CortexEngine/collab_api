package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
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
    @DisplayName("Should create work time")
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

        assertEquals("Commercial", result.description());
        verify(workTimeRepository).save(entity);
    }

    @Test
    @DisplayName("Should get work time by id")
    void shouldGetWorkTimeById() {
        WorkTime entity = new WorkTime(1L);
        WorkTimeResponseDTO response = new WorkTimeResponseDTO("Commercial", true, null, null, false, null, null, true, false);

        when(workTimeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(workTimeMapper.toResponse(entity)).thenReturn(response);

        WorkTimeResponseDTO result = workTimeService.getWorkTimeById(1L);

        assertEquals("Commercial", result.description());
    }
}
