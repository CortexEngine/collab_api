package com.example.collab.controller;

import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DepartmentController Tests")
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DepartmentService departmentService;

    private DepartmentRequestDTO departmentRequestDTO;
    private DepartmentResponseDTO departmentResponseDTO;

    @BeforeEach
    void setUp() {

        // Setup request DTO
        departmentRequestDTO = new DepartmentRequestDTO();
        departmentRequestDTO.setName("Engineering");
        departmentRequestDTO.setNumber(101);
        departmentRequestDTO.setInitialDate(LocalDate.of(2024, 1, 1));
        departmentRequestDTO.setEndDate(LocalDate.of(2026, 12, 31));
        departmentRequestDTO.setManagerRegistration(1001);
        departmentRequestDTO.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        departmentRequestDTO.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

        // Setup response DTO
        departmentResponseDTO = new DepartmentResponseDTO();
        departmentResponseDTO.setName("Engineering");
        departmentResponseDTO.setNumber(101);
        departmentResponseDTO.setInitialDate(LocalDate.of(2024, 1, 1));
        departmentResponseDTO.setEndDate(LocalDate.of(2026, 12, 31));
        departmentResponseDTO.setManagerRegistration(1001);
        departmentResponseDTO.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        departmentResponseDTO.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

    }

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartmentSuccessfully() throws Exception {

        // Arrange
        when(departmentService.createDepartment(any(DepartmentRequestDTO.class)))
            .thenReturn(departmentResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.number").value(101))
                .andExpect(jsonPath("$.managerRegistration").value(1001));

        verify(departmentService, times(1)).createDepartment(any(DepartmentRequestDTO.class));

    }

    @Test
    @DisplayName("Should get all departments successfully")
    void shouldGetAllDepartmentsSuccessfully() throws Exception {

        // Arrange
        List<DepartmentResponseDTO> departments = Arrays.asList(departmentResponseDTO);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Engineering"))
                .andExpect(jsonPath("$[0].number").value(101));

        verify(departmentService, times(1)).getAllDepartments();

    }

    @Test
    @DisplayName("Should get department by number successfully")
    void shouldGetDepartmentByNumberSuccessfully() throws Exception {

        // Arrange
        when(departmentService.getDepartmentByNumber(101)).thenReturn(departmentResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/departments/number/{number}", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.number").value(101));

        verify(departmentService, times(1)).getDepartmentByNumber(101);

    }

    @Test
    @DisplayName("Should get department by name successfully")
    void shouldGetDepartmentByNameSuccessfully() throws Exception {

        // Arrange
        when(departmentService.getDepartmentByName("Engineering")).thenReturn(departmentResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/departments/name/{name}", "Engineering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.number").value(101));

        verify(departmentService, times(1)).getDepartmentByName("Engineering");

    }

    @Test
    @DisplayName("Should get departments by manager registration successfully")
    void shouldGetDepartmentsByManagerRegistrationSuccessfully() throws Exception {

        // Arrange
        List<DepartmentResponseDTO> departments = Arrays.asList(departmentResponseDTO);
        when(departmentService.getDepartmentsByManagerRegistration(1001)).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/departments/manager/{registration}", 1001))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].managerRegistration").value(1001));

        verify(departmentService, times(1)).getDepartmentsByManagerRegistration(1001);

    }

    @Test
    @DisplayName("Should get departments by support manager registration successfully")
    void shouldGetDepartmentsBySupportManagerRegistrationSuccessfully() throws Exception {

        // Arrange
        List<DepartmentResponseDTO> departments = Arrays.asList(departmentResponseDTO);
        when(departmentService.getDepartmentsBySupportManagerRegistration(1002)).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/departments/support_manager/{registration}", 1002))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].managerSupportRegistration[0]").value(1002));

        verify(departmentService, times(1)).getDepartmentsBySupportManagerRegistration(1002);

    }

    @Test
    @DisplayName("Should get departments by team member registration successfully")
    void shouldGetDepartmentsByTeamMemberRegistrationSuccessfully() throws Exception {

        // Arrange
        List<DepartmentResponseDTO> departments = Arrays.asList(departmentResponseDTO);
        when(departmentService.getDepartmentsByTeamMemberRegistration(anyList())).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/departments/team_member")
                .param("registrations", "2001")
                .param("registrations", "2002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teamMembersRegistration[0]").value(2001));

        verify(departmentService, times(1)).getDepartmentsByTeamMemberRegistration(anyList());

    }

    @Test
    @DisplayName("Should update department successfully")
    void shouldUpdateDepartmentSuccessfully() throws Exception {

        // Arrange
        when(departmentService.updateDepartment(eq(101), any(DepartmentRequestDTO.class)))
            .thenReturn(departmentResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/departments/{number}", 101)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.number").value(101));

        verify(departmentService, times(1)).updateDepartment(eq(101), any(DepartmentRequestDTO.class));

    }

    @Test
    @DisplayName("Should delete department successfully")
    void shouldDeleteDepartmentSuccessfully() throws Exception {

        // Arrange
        when(departmentService.deleteDepartmentByNumber(101)).thenReturn(101);

        // Act & Assert
        mockMvc.perform(delete("/departments/{number}", 101))
                .andExpect(status().isNoContent())
                .andExpect(content().string("101"));

        verify(departmentService, times(1)).deleteDepartmentByNumber(101);

    }

    @Test
    @DisplayName("Should return bad request when creating department with invalid data")
    void shouldReturnBadRequestWhenCreatingDepartmentWithInvalidData() throws Exception {

        // Arrange
        DepartmentRequestDTO invalidRequest = new DepartmentRequestDTO();
        // Name and number are null (invalid)

        // Act & Assert
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should return bad request when number is not positive")
    void shouldReturnBadRequestWhenNumberIsNotPositive() throws Exception {

        // Arrange
        departmentRequestDTO.setNumber(-1);

        // Act & Assert
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should return bad request when name is blank")
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {

        // Arrange
        departmentRequestDTO.setName("");

        // Act & Assert
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)))
                .andExpect(status().isBadRequest());

    }
}
