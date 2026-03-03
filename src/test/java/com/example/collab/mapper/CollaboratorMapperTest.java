package com.example.collab.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;

@DisplayName("CollaboratorMapper Tests")
class CollaboratorMapperTest {

    private final CollaboratorMapper collaboratorMapper = Mappers.getMapper(CollaboratorMapper.class);

    @Test
    @DisplayName("Should map request dto to entity")
    void shouldMapRequestDtoToEntity() {
        CollaboratorRequestDTO request = new CollaboratorRequestDTO(
            "John Doe",
            LocalDate.of(1990, 5, 15),
            "Single",
            "Brazilian",
            "john.doe@example.com",
            "(11) 98765-4321",
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

        Collaborator result = collaboratorMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(12345, result.getRegistration());
        assertFalse(result.getManager());
        assertFalse(result.getSupportManager());
    }

    @Test
    @DisplayName("Should map entity to response dto")
    void shouldMapEntityToResponseDto() {
        Collaborator collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setRegistration(12345);
        collaborator.setManager(false);
        collaborator.setSupportManager(false);

        CollaboratorResponseDTO response = collaboratorMapper.toResponse(collaborator);

        assertNotNull(response);
        assertEquals("John Doe", response.name());
        assertEquals(12345, response.registration());
        assertFalse(response.manager());
        assertFalse(response.supportManager());
    }
}
