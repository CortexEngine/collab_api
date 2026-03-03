package com.example.collab.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;
import com.example.collab.exception.resource.ScheduleRotationNotFoundException;
import com.example.collab.service.ScheduleRotationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ScheduleRotationController.class)
@DisplayName("ScheduleRotationController Tests")
public class ScheduleRotationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScheduleRotationService scheduleRotationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ScheduleRotationRequestDTO requestDTO;
    private ScheduleRotationResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ScheduleRotationRequestDTO(1L, 2L, List.of(1, 2, 3), List.of(true, true, true));
        responseDTO = new ScheduleRotationResponseDTO(1, 2, List.of(1, 2, 3), List.of(true, true, true));
    }

    @Test
    @DisplayName("POST /schedule-rotations - Should create schedule rotation successfully")
    void testCreateScheduleRotation_Success() throws Exception {
        when(scheduleRotationService.createScheduleRotation(any()))
            .thenReturn(responseDTO);

        mockMvc.perform(post("/schedule-rotations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.dayIndexs", hasSize(3)));

        verify(scheduleRotationService, times(1)).createScheduleRotation(any());
    }

    @Test
    @DisplayName("POST /schedule-rotations - Should fail with invalid request body")
    void testCreateScheduleRotation_InvalidBody() throws Exception {
        ScheduleRotationRequestDTO invalidDTO = new ScheduleRotationRequestDTO(null, 2L, List.of(1), List.of(true));

        mockMvc.perform(post("/schedule-rotations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).createScheduleRotation(any());
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId} - Should retrieve schedule rotation successfully")
    void testGetByWorkScheduleId_Success() throws Exception {
        when(scheduleRotationService.getScheduleRotationByWorkScheduleId(1L))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.dayIndexs", hasSize(3)));

        verify(scheduleRotationService, times(1)).getScheduleRotationByWorkScheduleId(1L);
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId} - Should fail with invalid ID")
    void testGetByWorkScheduleId_InvalidId() throws Exception {
        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).getScheduleRotationByWorkScheduleId(any());
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId}/work-time/{workTimeId} - Should retrieve successfully")
    void testGetByWorkScheduleIdAndWorkTimeId_Success() throws Exception {
        when(scheduleRotationService.getScheduleRotationByWorkScheduleIdAndWorkTimeId(1L, 2L))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}/work-time/{workTimeId}", 1, 2)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.dayIndexs", hasSize(3)));

        verify(scheduleRotationService, times(1)).getScheduleRotationByWorkScheduleIdAndWorkTimeId(1L, 2L);
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId}/work-time/{workTimeId} - Should fail with invalid IDs")
    void testGetByWorkScheduleIdAndWorkTimeId_InvalidIds() throws Exception {
        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}/work-time/{workTimeId}", 0, -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).getScheduleRotationByWorkScheduleIdAndWorkTimeId(any(), any());
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId}/day-index/{dayIndexs} - Should retrieve successfully")
    void testGetByWorkScheduleAndDayIndexs_Success() throws Exception {
        when(scheduleRotationService.getScheduleRotationByWorkScheduleAndDayIndexs(1L, 1))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}/day-index/{dayIndexs}", 1, 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.dayIndexs", hasSize(3)));

        verify(scheduleRotationService, times(1)).getScheduleRotationByWorkScheduleAndDayIndexs(1L, 1);
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId}/day-index/{dayIndexs} - Should fail with invalid day index")
    void testGetByWorkScheduleAndDayIndexs_InvalidDayIndex() throws Exception {
        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}/day-index/{dayIndexs}", 1, 0)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).getScheduleRotationByWorkScheduleAndDayIndexs(any(), any());
    }

    @Test
    @DisplayName("PUT /schedule-rotations/{id} - Should update schedule rotation successfully")
    void testUpdateScheduleRotation_Success() throws Exception {
        when(scheduleRotationService.updateScheduleRotation(1L, requestDTO))
            .thenReturn(responseDTO);

        mockMvc.perform(put("/schedule-rotations/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()));

        verify(scheduleRotationService, times(1)).updateScheduleRotation(1L, requestDTO);
    }

    @Test
    @DisplayName("PUT /schedule-rotations/{id} - Should fail with invalid ID")
    void testUpdateScheduleRotation_InvalidId() throws Exception {
        mockMvc.perform(put("/schedule-rotations/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).updateScheduleRotation(any(), any());
    }

    @Test
    @DisplayName("DELETE /schedule-rotations/work-schedule/{workScheduleId} - Should delete successfully")
    void testDeleteScheduleRotation_Success() throws Exception {
        doNothing().when(scheduleRotationService).deleteScheduleRotation(1L);

        mockMvc.perform(delete("/schedule-rotations/work-schedule/{workScheduleId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(scheduleRotationService, times(1)).deleteScheduleRotation(1L);
    }

    @Test
    @DisplayName("DELETE /schedule-rotations/work-schedule/{workScheduleId} - Should fail with invalid ID")
    void testDeleteScheduleRotation_InvalidId() throws Exception {
        mockMvc.perform(delete("/schedule-rotations/work-schedule/{workScheduleId}", 0)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(scheduleRotationService, never()).deleteScheduleRotation(any());
    }

    @Test
    @DisplayName("GET /schedule-rotations/work-schedule/{workScheduleId} - Should handle not found exception")
    void testGetByWorkScheduleId_NotFound() throws Exception {
        when(scheduleRotationService.getScheduleRotationByWorkScheduleId(999L))
            .thenThrow(new ScheduleRotationNotFoundException("Schedule rotation not found"));

        mockMvc.perform(get("/schedule-rotations/work-schedule/{workScheduleId}", 999)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
