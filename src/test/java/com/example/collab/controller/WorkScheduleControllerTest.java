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

import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;
import com.example.collab.exception.resource.WorkScheduleNotFoundException;
import com.example.collab.service.WorkScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WorkScheduleController.class)
@DisplayName("WorkScheduleController Tests")
public class WorkScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkScheduleService workScheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkScheduleRequestDTO requestDTO;
    private WorkScheduleResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new WorkScheduleRequestDTO("Monday to Friday", 5, 2, true);
        responseDTO = new WorkScheduleResponseDTO("Monday to Friday", 5, 2, true);
    }

    @Test
    @DisplayName("POST /work-schedules - Should create work schedule successfully")
    void testCreateWorkSchedule_Success() throws Exception {
        when(workScheduleService.createWorkSchedule(any()))
            .thenReturn(responseDTO);

        mockMvc.perform(post("/work-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description", is("Monday to Friday")))
            .andExpect(jsonPath("$.workDaysPerWeek", is(5)));

        verify(workScheduleService, times(1)).createWorkSchedule(any());
    }

    @Test
    @DisplayName("POST /work-schedules - Should fail with invalid request body")
    void testCreateWorkSchedule_InvalidBody() throws Exception {
        WorkScheduleRequestDTO invalidDTO = new WorkScheduleRequestDTO(null, 5, 2, true);

        mockMvc.perform(post("/work-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).createWorkSchedule(any());
    }

    @Test
    @DisplayName("POST /work-schedules - Should fail with invalid work days")
    void testCreateWorkSchedule_InvalidWorkDays() throws Exception {
        WorkScheduleRequestDTO invalidDTO = new WorkScheduleRequestDTO("Test", 0, 2, true);

        mockMvc.perform(post("/work-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).createWorkSchedule(any());
    }

    @Test
    @DisplayName("GET /work-schedules - Should retrieve all work schedules")
    void testGetAllWorkSchedule_Success() throws Exception {
        WorkScheduleResponseDTO dto2 = new WorkScheduleResponseDTO("Saturday", 1, 6, false);
        when(workScheduleService.getAllWorkSchedule())
            .thenReturn(List.of(responseDTO, dto2));

        mockMvc.perform(get("/work-schedules")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].description", is("Monday to Friday")))
            .andExpect(jsonPath("$[1].description", is("Saturday")));

        verify(workScheduleService, times(1)).getAllWorkSchedule();
    }

    @Test
    @DisplayName("GET /work-schedules/{id} - Should retrieve work schedule by ID")
    void testGetWorkScheduleById_Success() throws Exception {
        when(workScheduleService.getActiveWorkScheduleById(1L))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/work-schedules/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is("Monday to Friday")));

        verify(workScheduleService, times(1)).getActiveWorkScheduleById(1L);
    }

    @Test
    @DisplayName("GET /work-schedules/{id} - Should fail with invalid ID")
    void testGetWorkScheduleById_InvalidId() throws Exception {
        mockMvc.perform(get("/work-schedules/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).getActiveWorkScheduleById(any());
    }

    @Test
    @DisplayName("GET /work-schedules/description/{description} - Should retrieve by description")
    void testGetByDescription_Success() throws Exception {
        when(workScheduleService.getWorkScheduleByDescription("Monday to Friday"))
            .thenReturn(responseDTO);

        mockMvc.perform(get("/work-schedules/description/{description}", "Monday to Friday")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is("Monday to Friday")));

        verify(workScheduleService, times(1)).getWorkScheduleByDescription("Monday to Friday");
    }

    @Test
    @DisplayName("GET /work-schedules/description/{description} - Should fail with blank description")
    void testGetByDescription_BlankDescription() throws Exception {
        mockMvc.perform(get("/work-schedules/description/{description}", " ")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).getWorkScheduleByDescription(any());
    }

    @Test
    @DisplayName("GET /work-schedules/active - Should retrieve all active schedules")
    void testGetAllActive_Success() throws Exception {
        when(workScheduleService.getAllActiveWorkSchedule())
            .thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/work-schedules/active")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].isActive", is(true)));

        verify(workScheduleService, times(1)).getAllActiveWorkSchedule();
    }

    @Test
    @DisplayName("GET /work-schedules/deactivated - Should retrieve all deactivated schedules")
    void testGetAllDeactivated_Success() throws Exception {
        WorkScheduleResponseDTO inactiveDTO = new WorkScheduleResponseDTO("Inactive", 5, 2, false);
        when(workScheduleService.getAllDeactivatedWorkSchedule())
            .thenReturn(List.of(inactiveDTO));

        mockMvc.perform(get("/work-schedules/deactivated")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].isActive", is(false)));

        verify(workScheduleService, times(1)).getAllDeactivatedWorkSchedule();
    }

    @Test
    @DisplayName("PUT /work-schedules/{id} - Should update work schedule successfully")
    void testUpdateWorkSchedule_Success() throws Exception {
        when(workScheduleService.updateWorkSchedule(1L, requestDTO))
            .thenReturn(responseDTO);

        mockMvc.perform(put("/work-schedules/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()));

        verify(workScheduleService, times(1)).updateWorkSchedule(1L, requestDTO);
    }

    @Test
    @DisplayName("PUT /work-schedules/{id} - Should fail with invalid ID")
    void testUpdateWorkSchedule_InvalidId() throws Exception {
        mockMvc.perform(put("/work-schedules/{id}", 0)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).updateWorkSchedule(any(), any());
    }

    @Test
    @DisplayName("PATCH /work-schedules/{id}/activate - Should activate work schedule")
    void testActivateWorkSchedule_Success() throws Exception {
        WorkScheduleResponseDTO activatedDTO = new WorkScheduleResponseDTO("Monday to Friday", 5, 2, true);
        when(workScheduleService.activateWorkSchedule(1L))
            .thenReturn(activatedDTO);

        mockMvc.perform(patch("/work-schedules/{id}/activate", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isActive", is(true)));

        verify(workScheduleService, times(1)).activateWorkSchedule(1L);
    }

    @Test
    @DisplayName("PATCH /work-schedules/{id}/deactivate - Should deactivate work schedule")
    void testDeactivateWorkSchedule_Success() throws Exception {
        WorkScheduleResponseDTO deactivatedDTO = new WorkScheduleResponseDTO("Monday to Friday", 5, 2, false);
        when(workScheduleService.deactivateWorkSchedule(1L))
            .thenReturn(deactivatedDTO);

        mockMvc.perform(patch("/work-schedules/{id}/deactivate", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isActive", is(false)));

        verify(workScheduleService, times(1)).deactivateWorkSchedule(1L);
    }

    @Test
    @DisplayName("DELETE /work-schedules/{id} - Should delete work schedule successfully")
    void testDeleteWorkSchedule_Success() throws Exception {
        doNothing().when(workScheduleService).deleteWorkSchedule(1L);

        mockMvc.perform(delete("/work-schedules/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(workScheduleService, times(1)).deleteWorkSchedule(1L);
    }

    @Test
    @DisplayName("DELETE /work-schedules/{id} - Should fail with invalid ID")
    void testDeleteWorkSchedule_InvalidId() throws Exception {
        mockMvc.perform(delete("/work-schedules/{id}", -1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(workScheduleService, never()).deleteWorkSchedule(any());
    }

    @Test
    @DisplayName("GET /work-schedules/{id} - Should handle not found exception")
    void testGetWorkScheduleById_NotFound() throws Exception {
        when(workScheduleService.getActiveWorkScheduleById(999L))
            .thenThrow(new WorkScheduleNotFoundException("Work schedule not found"));

        mockMvc.perform(get("/work-schedules/{id}", 999)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
