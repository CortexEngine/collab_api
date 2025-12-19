package com.example.collab.controller;

import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.service.CollaboratorService;
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
@DisplayName("CollaboratorController Tests")
class CollaboratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CollaboratorService collaboratorService;

    private CollaboratorRequestDTO collaboratorRequestDTO;
    private CollaboratorResponseDTO collaboratorResponseDTO;

    @BeforeEach
    void setUp() {

        // Setup Request DTO
        collaboratorRequestDTO = new CollaboratorRequestDTO();
        collaboratorRequestDTO.setName("John Doe");
        collaboratorRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        collaboratorRequestDTO.setMaritalStatus("Single");
        collaboratorRequestDTO.setNationality("Brazilian");
        collaboratorRequestDTO.setEmail("john.doe@example.com");
        collaboratorRequestDTO.setPhone("+55 11 987654321");
        collaboratorRequestDTO.setAddress("123 Main St");
        collaboratorRequestDTO.setPosition("Software Engineer");
        collaboratorRequestDTO.setDepartment(101);
        collaboratorRequestDTO.setManager(false);
        collaboratorRequestDTO.setSupportManager(false);
        collaboratorRequestDTO.setAdmissionDate(LocalDate.now().plusDays(1));
        collaboratorRequestDTO.setContractType("CLT");
        collaboratorRequestDTO.setSalary(5000.0);
        collaboratorRequestDTO.setRegistration(12345);
        collaboratorRequestDTO.setWorkload("40h");
        collaboratorRequestDTO.setBank("001");
        collaboratorRequestDTO.setAgency("1234");
        collaboratorRequestDTO.setAccount("12345-6");
        collaboratorRequestDTO.setTypeAccount("Corrente");
        collaboratorRequestDTO.setPix("john.doe@example.com");
        collaboratorRequestDTO.setWorkWallet("12345678");
        collaboratorRequestDTO.setCPF("046.797.650-38");
        collaboratorRequestDTO.setRG("16.387.753-1");

        // Setup Response DTO
        collaboratorResponseDTO = new CollaboratorResponseDTO();
        collaboratorResponseDTO.setName("John Doe");
        collaboratorResponseDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        collaboratorResponseDTO.setMaritalStatus("Single");
        collaboratorResponseDTO.setNationality("Brazilian");
        collaboratorResponseDTO.setEmail("john.doe@example.com");
        collaboratorResponseDTO.setPhone("+55 11 987654321");
        collaboratorResponseDTO.setAddress("123 Main St");
        collaboratorResponseDTO.setPosition("Software Engineer");
        collaboratorResponseDTO.setDepartment(101);
        collaboratorResponseDTO.setManager(false);
        collaboratorResponseDTO.setSupportManager(false);
        collaboratorResponseDTO.setAdmissionDate(LocalDate.now().plusDays(1));
        collaboratorResponseDTO.setContractType("CLT");
        collaboratorResponseDTO.setSalary(5000.0);
        collaboratorResponseDTO.setRegistration(12345);
        collaboratorResponseDTO.setWorkload("40h");
        collaboratorResponseDTO.setBank("001");
        collaboratorResponseDTO.setAgency("1234");
        collaboratorResponseDTO.setAccount("12345-6");
        collaboratorResponseDTO.setTypeAccount("Corrente");
        collaboratorResponseDTO.setPix("john.doe@example.com");
        collaboratorResponseDTO.setWorkWallet("12345678");
        collaboratorResponseDTO.setCPF("046.797.650-38");
        collaboratorResponseDTO.setRG("16.387.753-1");

    }

    @Test
    @DisplayName("Should create collaborator successfully")
    void shouldCreateCollaboratorSuccessfully() throws Exception {

        // Arrange
        when(collaboratorService.createCollaborator(any(CollaboratorRequestDTO.class)))
                .thenReturn(collaboratorResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/collaborators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.registration").value(12345))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(collaboratorService, times(1)).createCollaborator(any(CollaboratorRequestDTO.class));

    }

    @Test
    @DisplayName("Should get all collaborators successfully")
    void shouldGetAllCollaboratorsSuccessfully() throws Exception {

        // Arrange
        List<CollaboratorResponseDTO> collaborators = Arrays.asList(collaboratorResponseDTO);
        when(collaboratorService.getAllCollaborators()).thenReturn(collaborators);

        // Act & Assert
        mockMvc.perform(get("/collaborators"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].registration").value(2001));

        verify(collaboratorService, times(1)).getAllCollaborators();

    }

    @Test
    @DisplayName("Should get collaborator by registration successfully")
    void shouldGetCollaboratorByRegistrationSuccessfully() throws Exception {

        // Arrange
        when(collaboratorService.getCollaboratorByRegistration(2001)).thenReturn(collaboratorResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/collaborators/registration/{registration}", 2001))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.registration").value(2001));

        verify(collaboratorService, times(1)).getCollaboratorByRegistration(2001);

    }

    @Test
    @DisplayName("Should get collaborator by CPF successfully")
    void shouldGetCollaboratorByCPFSuccessfully() throws Exception {

        // Arrange
        when(collaboratorService.getCollaboratorByCPF("046.797.650-38")).thenReturn(collaboratorResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/collaborators/cpf/{cpf}", "046.797.650-38"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.cpf").value("046.797.650-38"));

        verify(collaboratorService, times(1)).getCollaboratorByCPF("046.797.650-38");

    }

    @Test
    @DisplayName("Should get collaborators by name successfully")
    void shouldGetCollaboratorsByNameSuccessfully() throws Exception {

        // Arrange
        List<CollaboratorResponseDTO> collaborators = Arrays.asList(collaboratorResponseDTO);
        when(collaboratorService.getCollaboratorByName("John Doe")).thenReturn(collaborators);

        // Act & Assert
        mockMvc.perform(get("/collaborators/name/{name}", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(collaboratorService, times(1)).getCollaboratorByName("John Doe");

    }

    @Test
    @DisplayName("Should get collaborators by position successfully")
    void shouldGetCollaboratorsByPositionSuccessfully() throws Exception {

        // Arrange
        List<CollaboratorResponseDTO> collaborators = Arrays.asList(collaboratorResponseDTO);
        when(collaboratorService.getCollaboratorByPosition("Developer")).thenReturn(collaborators);

        // Act & Assert
        mockMvc.perform(get("/collaborators/position/{position}", "Developer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].position").value("Developer"));

        verify(collaboratorService, times(1)).getCollaboratorByPosition("Developer");

    }

    @Test
    @DisplayName("Should get collaborators by bank successfully")
    void shouldGetCollaboratorsByBankSuccessfully() throws Exception {

        // Arrange
        List<CollaboratorResponseDTO> collaborators = Arrays.asList(collaboratorResponseDTO);
        when(collaboratorService.getCollaboratorByBank("001")).thenReturn(collaborators);

        // Act & Assert
        mockMvc.perform(get("/collaborators/bank/{bank}", "001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bank").value("001"));

        verify(collaboratorService, times(1)).getCollaboratorByBank("001");

    }

    @Test
    @DisplayName("Should update collaborator successfully")
    void shouldUpdateCollaboratorSuccessfully() throws Exception {

        // Arrange
        collaboratorRequestDTO.setName("John Doe Updated");
        collaboratorResponseDTO.setName("John Doe Updated");
        
        when(collaboratorService.updateCollaborator(eq(2001), any(CollaboratorRequestDTO.class)))
                .thenReturn(collaboratorResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/collaborators/{registration}", 2001)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"));

        verify(collaboratorService, times(1)).updateCollaborator(eq(2001), any(CollaboratorRequestDTO.class));

    }

    @Test
    @DisplayName("Should delete collaborator by registration successfully")
    void shouldDeleteCollaboratorByRegistrationSuccessfully() throws Exception {

        // Arrange
        when(collaboratorService.deleteCollaboratorByRegistration(2001))
                .thenReturn("Collaborator deleted successfully");

        // Act & Assert
        mockMvc.perform(delete("/collaborators/{registration}", 2001))
                .andExpect(status().isNoContent());

        verify(collaboratorService, times(1)).deleteCollaboratorByRegistration(2001);

    }

    @Test
    @DisplayName("Should validate required fields when creating collaborator")
    void shouldValidateRequiredFieldsWhenCreatingCollaborator() throws Exception {

        // Arrange - DTO com campos obrigatórios nulos
        CollaboratorRequestDTO invalidDTO = new CollaboratorRequestDTO();
        invalidDTO.setName(""); // @NotBlank

        // Act & Assert
        mockMvc.perform(post("/collaborators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(collaboratorService, never()).createCollaborator(any());

    }

    @Test
    @DisplayName("Should validate email format when creating collaborator")
    void shouldValidateEmailFormatWhenCreatingCollaborator() throws Exception {

        // Arrange
        collaboratorRequestDTO.setEmail("invalid-email");

        // Act & Assert
        mockMvc.perform(post("/collaborators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(collaboratorService, never()).createCollaborator(any());

    }

    @Test
    @DisplayName("Should validate past birth date when creating collaborator")
    void shouldValidatePastBirthDateWhenCreatingCollaborator() throws Exception {

        // Arrange
        collaboratorRequestDTO.setBirthDate(LocalDate.now().plusDays(1)); // Future date

        // Act & Assert
        mockMvc.perform(post("/collaborators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(collaboratorService, never()).createCollaborator(any());

    }

    @Test
    @DisplayName("Should validate future or present admission date when creating collaborator")
    void shouldValidateFutureOrPresentAdmissionDateWhenCreatingCollaborator() throws Exception {

        // Arrange
        collaboratorRequestDTO.setAdmissionDate(LocalDate.now().minusDays(1)); // Past date

        // Act & Assert - Note: @FutureOrPresent allows today, so we need a past date
        mockMvc.perform(post("/collaborators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collaboratorRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(collaboratorService, never()).createCollaborator(any());

    }

}
