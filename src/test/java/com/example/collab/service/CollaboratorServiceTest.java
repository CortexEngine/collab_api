package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.exception.resource.NotFoundCollaboratorException;
import com.example.collab.mapper.CollaboratorMapper;
import com.example.collab.repository.CollaboratorRepository;
import com.example.collab.service.validation.CollaboratorValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("CollaboratorService Tests")
class CollaboratorServiceTest {

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private CollaboratorValidator collaboratorValidator;

    @Mock
    private CollaboratorMapper collaboratorMapper;

    @InjectMocks
    private CollaboratorService collaboratorService;

    @Test
    @DisplayName("Should create collaborator successfully")
    void shouldCreateCollaboratorSuccessfully() {
        CollaboratorRequestDTO request = new CollaboratorRequestDTO(
            "John Doe",
            LocalDate.of(1990, 5, 15),
            "Single",
            "Brazilian",
            "john.doe@example.com",
            "+55 16 9992-1821",
            "Main Street",
            "Software Engineer",
            101,
            false,
            false,
            LocalDate.now().plusDays(1),
            "CLT",
            5000.0,
            12345,
            null,
            "001",
            "1234",
            "123456789012-3",
            "Checking",
            "john.doe@example.com",
            "12345678901",
            null,
            null,
            "32630500000",
            "12345678901",
            "046.797.650-38",
            "16.387.753-1",
            null,
            null,
            null,
            null,
            null
        );

        Collaborator collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setRegistration(12345);

        CollaboratorResponseDTO response = new CollaboratorResponseDTO(
            1L,
            "John Doe",
            LocalDate.of(1990, 5, 15),
            "Single",
            "Brazilian",
            "john.doe@example.com",
            "+55 16 9992-1821",
            "Main Street",
            "Software Engineer",
            101,
            false,
            false,
            LocalDate.now().plusDays(1),
            "CLT",
            5000.0,
            12345,
            null,
            "001",
            "1234",
            "123456789012-3",
            "Checking",
            "john.doe@example.com",
            "12345678901",
            null,
            null,
            "32630500000",
            "12345678901",
            "046.797.650-38",
            "16.387.753-1",
            null,
            null,
            null,
            null,
            null
        );

        when(collaboratorMapper.toEntity(request)).thenReturn(collaborator);
        when(collaboratorRepository.save(collaborator)).thenReturn(collaborator);
        when(collaboratorMapper.toResponse(collaborator)).thenReturn(response);

        CollaboratorResponseDTO result = collaboratorService.createCollaborator(request);

        assertNotNull(result);
        assertEquals("John Doe", result.name());
        verify(collaboratorRepository).save(collaborator);
    }

    @Test
    @DisplayName("Should get collaborator by registration")
    void shouldGetCollaboratorByRegistration() {
        Collaborator collaborator = new Collaborator();
        collaborator.setRegistration(12345);

        CollaboratorResponseDTO response = new CollaboratorResponseDTO(
            1L, "John Doe", null, null, null, null, null, null, null,
            null, false, false, null, null, null, 12345, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.of(collaborator));
        when(collaboratorMapper.toResponse(collaborator)).thenReturn(response);

        CollaboratorResponseDTO result = collaboratorService.getCollaboratorByRegistration(12345);

        assertEquals(12345, result.registration());
    }

    @Test
    @DisplayName("Should throw when registration not found")
    void shouldThrowWhenRegistrationNotFound() {
        when(collaboratorRepository.findByRegistration(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundCollaboratorException.class, () -> collaboratorService.getCollaboratorByRegistration(9));
    }
}
