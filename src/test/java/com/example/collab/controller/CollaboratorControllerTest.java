package com.example.collab.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.service.CollaboratorService;

@WebMvcTest(CollaboratorController.class)
@DisplayName("CollaboratorController Tests")
class CollaboratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CollaboratorService collaboratorService;

    @Test
    @DisplayName("Should list all collaborators")
    void shouldListAllCollaborators() throws Exception {
        CollaboratorResponseDTO response = new CollaboratorResponseDTO(
            1L, "John Doe", null, null, null, null, null, null, null,
            null, false, false, null, null, null, null, 12345,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null
        );

        when(collaboratorService.getAllCollaborators()).thenReturn(List.of(response));

        mockMvc.perform(get("/collaborators"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("John Doe"));
    }
}
