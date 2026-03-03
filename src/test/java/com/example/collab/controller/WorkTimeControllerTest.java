package com.example.collab.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;
import com.example.collab.exception.resource.NotFoundException;
import com.example.collab.service.WorkTimeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WorkTimeController.class)
@DisplayName("WorkTimeController Tests")
public class WorkTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkTimeService workTimeService;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkTimeRequestDTO requestDTO;
    private WorkTimeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new WorkTimeRequestDTO(
            "Morning Shift",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            false,
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            true,
            true
        );
        responseDTO = new WorkTimeResponseDTO(
            "Morning Shift",
            true,
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            false,
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            true,
            true
        );
    }

    @Test
    @DisplayName("POST /work-time - Should create work time successfully")
    void testCreateWorkTime_Success() throws Exception {
        when(workTimeService.createWorkTime(any()))
            .thenReturn(responseDTO);

        mockMvc.perform(post("/work-time")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description", is("Morning Shift")))
            .andExpect(jsonPath("$.isActive", is(true)));

        verify(workTimeService, times(1)).createWorkTime(any());
    }

    @Test
    @DisplayName("POST /work-time - Should fail with invalid request body")
    void testCreateWorkTime_InvalidBody() throws Exception {
        WorkTimeRequestDTO invalidDTO = new WorkTimeRequestDTO(
            null,
            true,
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            false,
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            true,
            true
        );

        mockMvc.perform(post("/work-time")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).createWorkTime(any());
    }

    @Test
    @DisplayName("POST /work-time - Should fail with null times")
    void testCreateWorkTime_NullTimes() throws Exception {
        WorkTimeRequestDTO invalidDTO = new WorkTimeRequestDTO(
            "Test",
            true,
            null,
            LocalTime.of(12, 0),
            false,
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            true,
            true
        );

        mockMvc.perform(post("/work-time")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).createWorkTime(any());
    }

    @Test
    @DisplayName("GET /work-time - Should retrieve all work times")
    void testGetAllWorkTimes_Success() throws Exception {
        WorkTimeResponseDTO dto2 = new WorkTimeResponseDTO(
            "Evening Shift",
            true,
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            false,
            LocalTime.of(16, 0),
            LocalTime.of(16, 30),
            true,
            true
        );
        when(workTimeService.getAllWorkTimes())
            .thenReturn(List.of(responseDTO, dto2));

        mockMvc.perform(get("/work-time")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].description", is("Morning Shift")))
            .andExpect(jsonPath("$[1].description", is("Evening Shift")));

        verify(workTimeService, times(1)).getAllWorkTimes();
    }

    @Test
    @DisplayName("GET /work-time/{id} - Should retrieve work time by ID")
    void testGetWorkTimeById_Success() throws Exception {
        when(workTimeService.getWorkTimeById(1L))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/work-time/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is("Morning Shift")))
            .andExpect(jsonPath("$.isActive", is(true)));

        verify(workTimeService, times(1)).getWorkTimeById(1L);
    }

    @Test
    @DisplayName("GET /work-time/{id} - Should fail with invalid ID")
    void testGetWorkTimeById_InvalidId() throws Exception {
        mockMvc.perform(get("/work-time/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).getWorkTimeById(any());
    }

    @Test
    @DisplayName("GET /work-time/{id} - Should fail with zero ID")
    void testGetWorkTimeById_ZeroId() throws Exception {
        mockMvc.perform(get("/work-time/{id}", 0)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).getWorkTimeById(any());
    }

    @Test
    @DisplayName("GET /work-time/active - Should retrieve all active work times")
    void testGetActiveWorkTimes_Success() throws Exception {
        when(workTimeService.getActiveWorkTimes())
            .thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/work-time/active")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].isActive", is(true)));

        verify(workTimeService, times(1)).getActiveWorkTimes();
    }

    @Test
    @DisplayName("PUT /work-time/{id} - Should update work time successfully")
    void testUpdateWorkTime_Success() throws Exception {
        when(workTimeService.updateWorkTime(1L, requestDTO))
            .thenReturn(responseDTO);

        mockMvc.perform(put("/work-time/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is("Morning Shift")));

        verify(workTimeService, times(1)).updateWorkTime(1L, requestDTO);
    }

    @Test
    @DisplayName("PUT /work-time/{id} - Should fail with invalid ID")
    void testUpdateWorkTime_InvalidId() throws Exception {
        mockMvc.perform(put("/work-time/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).updateWorkTime(any(), any());
    }

    @Test
    @DisplayName("PATCH /work-time/{id}/activate - Should activate work time")
    void testActivateWorkTime_Success() throws Exception {
        when(workTimeService.activateWorkTime(1L))
            .thenReturn(responseDTO);

        mockMvc.perform(patch("/work-time/{id}/activate", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isActive", is(true)));

        verify(workTimeService, times(1)).activateWorkTime(1L);
    }

    @Test
    @DisplayName("PATCH /work-time/{id}/deactivate - Should deactivate work time")
    void testDeactivateWorkTime_Success() throws Exception {
        WorkTimeResponseDTO deactivatedDTO = new WorkTimeResponseDTO(
            "Morning Shift",
            false,
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            false,
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            true,
            true
        );
        when(workTimeService.deactivateWorkTime(1L))
            .thenReturn(deactivatedDTO);

        mockMvc.perform(patch("/work-time/{id}/deactivate", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isActive", is(false)));

        verify(workTimeService, times(1)).deactivateWorkTime(1L);
    }

    @Test
    @DisplayName("PATCH /work-time/{id}/activate - Should fail with invalid ID")
    void testActivateWorkTime_InvalidId() throws Exception {
        mockMvc.perform(patch("/work-time/{id}/activate", 0)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).activateWorkTime(any());
    }

    @Test
    @DisplayName("PATCH /work-time/{id}/deactivate - Should fail with invalid ID")
    void testDeactivateWorkTime_InvalidId() throws Exception {
        mockMvc.perform(patch("/work-time/{id}/deactivate", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workTimeService, never()).deactivateWorkTime(any());
    }

    @Test
    @DisplayName("GET /work-time/{id} - Should handle not found exception")
    void testGetWorkTimeById_NotFound() throws Exception {
        when(workTimeService.getWorkTimeById(999L))
            .thenThrow(new NotFoundException("Work time not found"));

        mockMvc.perform(get("/work-time/{id}", 999)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /work-time - Should return empty list when no work times exist")
    void testGetAllWorkTimes_Empty() throws Exception {
        when(workTimeService.getAllWorkTimes())
            .thenReturn(List.of());

        mockMvc.perform(get("/work-time")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(workTimeService, times(1)).getAllWorkTimes();
    }

    @Test
    @DisplayName("GET /work-time/active - Should return empty list when no active work times exist")
    void testGetActiveWorkTimes_Empty() throws Exception {
        when(workTimeService.getActiveWorkTimes())
            .thenReturn(List.of());

        mockMvc.perform(get("/work-time/active")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(workTimeService, times(1)).getActiveWorkTimes();
    }
}
