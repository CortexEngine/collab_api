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
    @DisplayName("Should create schedule rotation")
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

        assertEquals(5, result.dayIndexs().size());
    }

    @Test
    @DisplayName("Should get schedule rotation by work schedule id")
    void shouldGetScheduleRotationByWorkScheduleId() {
        ScheduleRotation entity = new ScheduleRotation();
        ScheduleRotationResponseDTO response = new ScheduleRotationResponseDTO(1, 1, List.of(1), List.of(true));

        when(scheduleRotationRepository.findByWorkScheduleId(1L)).thenReturn(Optional.of(entity));
        when(scheduleRotationMapper.toResponse(entity)).thenReturn(response);

        ScheduleRotationResponseDTO result = scheduleRotationService.getScheduleRotationByWorkScheduleId(1L);

        assertEquals(1, result.workSchedule());
    }
}
