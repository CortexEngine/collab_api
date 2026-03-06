package com.example.collab.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DepartmentController.class)
@DisplayName("DepartmentController Tests")
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("Should create department")
    void shouldCreateDepartment() throws Exception {
        DepartmentRequestDTO request = new DepartmentRequestDTO(
            "Engineering",
            101,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2026, 12, 31),
            1001,
            List.of(1002, 1003),
            List.of(2001, 2002, 2003)
        );

        DepartmentResponseDTO response = new DepartmentResponseDTO(
            "Engineering",
            101,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2026, 12, 31),
            1001,
            List.of(1002, 1003),
            List.of(2001, 2002, 2003)
        );

        when(departmentService.createDepartment(any(DepartmentRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Engineering"))
            .andExpect(jsonPath("$.number").value(101));
    }

    @Test
    @DisplayName("Should list departments")
    void shouldListDepartments() throws Exception {
        DepartmentResponseDTO response = new DepartmentResponseDTO("Engineering", 101, null, null, null, null, null);

        when(departmentService.getAllDepartments()).thenReturn(List.of(response));

        mockMvc.perform(get("/departments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Engineering"));
    }
}
