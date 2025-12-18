package com.example.collab.mapper;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CollaboratorMapper Tests")
class CollaboratorMapperTest {

    private CollaboratorMapper collaboratorMapper;

    private CollaboratorRequestDTO collaboratorRequestDTO;
    private Collaborator collaborator;

    @BeforeEach
    void setUp() {
        collaboratorMapper = Mappers.getMapper(CollaboratorMapper.class);

        // Setup request DTO
        collaboratorRequestDTO = new CollaboratorRequestDTO();
        collaboratorRequestDTO.setName("John Doe");
        collaboratorRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        collaboratorRequestDTO.setMaritalStatus("Single");
        collaboratorRequestDTO.setNationality("Brazilian");
        collaboratorRequestDTO.setEmail("john.doe@example.com");
        collaboratorRequestDTO.setPhone("+55 11 98765-4321");
        collaboratorRequestDTO.setAddress("123 Main St, São Paulo, SP");
        collaboratorRequestDTO.setPosition("Software Engineer");
        collaboratorRequestDTO.setDepartment(101);
        collaboratorRequestDTO.setManager(false);
        collaboratorRequestDTO.setSupportManager(false);
        collaboratorRequestDTO.setAdmissionDate(LocalDate.of(2024, 1, 1));
        collaboratorRequestDTO.setContractType("CLT");
        collaboratorRequestDTO.setSalary(5000.0);
        collaboratorRequestDTO.setRegistration(12345);
        collaboratorRequestDTO.setWorkload("40h");
        collaboratorRequestDTO.setBank("001");
        collaboratorRequestDTO.setAgency("1234");
        collaboratorRequestDTO.setAccount("123456789012-3");
        collaboratorRequestDTO.setTypeAccount("Checking");
        collaboratorRequestDTO.setPix("john.doe@example.com");
        collaboratorRequestDTO.setWorkWallet("12345678901");
        collaboratorRequestDTO.setVoterRegistration("123456789012");
        collaboratorRequestDTO.setReservistCertificate("AB123456");
        collaboratorRequestDTO.setPIS("32630500000");
        collaboratorRequestDTO.setCNH("12345678901");
        collaboratorRequestDTO.setCPF("046.797.650-38");
        collaboratorRequestDTO.setRG("16.387.753-1");
        collaboratorRequestDTO.setEmergencyContact("Jane Doe");
        collaboratorRequestDTO.setPhoneEmergency("+55 11 98765-4321");
        collaboratorRequestDTO.setEducation("Bachelor's Degree");
        collaboratorRequestDTO.setCourse("Computer Science");
        collaboratorRequestDTO.setObservations("Excellent employee");

        // Setup entity
        collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setBirthDate(LocalDate.of(1990, 5, 15));
        collaborator.setMaritalStatus("Single");
        collaborator.setNationality("Brazilian");
        collaborator.setAddress("123 Main St, São Paulo, SP");
        collaborator.setPosition("Software Engineer");
        collaborator.setManager(false);
        collaborator.setSupportManager(false);
        collaborator.setAdmissionDate(LocalDate.of(2024, 1, 1));
        collaborator.setContractType("CLT");
        collaborator.setSalary(5000.0);
        collaborator.setRegistration(12345);
        collaborator.setWorkload("40h");
        collaborator.setReservistCertificate("AB123456");
        collaborator.setEmergencyContact("Jane Doe");
        collaborator.setEducation("Bachelor's Degree");
        collaborator.setCourse("Computer Science");
        collaborator.setObservations("Excellent employee");
    }

    @Test
    @DisplayName("Should map CollaboratorRequestDTO to Collaborator entity")
    void shouldMapRequestDTOToEntity() {
        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(LocalDate.of(1990, 5, 15), result.getBirthDate());
        assertEquals("Single", result.getMaritalStatus());
        assertEquals("Brazilian", result.getNationality());
        assertEquals("123 Main St, São Paulo, SP", result.getAddress());
        assertEquals("Software Engineer", result.getPosition());
        assertEquals(LocalDate.of(2024, 1, 1), result.getAdmissionDate());
        assertEquals("CLT", result.getContractType());
        assertEquals(5000.0, result.getSalary());
        assertEquals(12345, result.getRegistration());
        assertEquals("40h", result.getWorkload());
        assertFalse(result.isManager());
        assertFalse(result.isSupportManager());
    }

    @Test
    @DisplayName("Should map Collaborator entity to CollaboratorResponseDTO")
    void shouldMapEntityToResponseDTO() {
        // Act
        CollaboratorResponseDTO result = collaboratorMapper.toResponse(collaborator);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(LocalDate.of(1990, 5, 15), result.getBirthDate());
        assertEquals("Single", result.getMaritalStatus());
        assertEquals("Brazilian", result.getNationality());
        assertEquals("123 Main St, São Paulo, SP", result.getAddress());
        assertEquals("Software Engineer", result.getPosition());
        assertEquals(LocalDate.of(2024, 1, 1), result.getAdmissionDate());
        assertEquals("CLT", result.getContractType());
        assertEquals(5000.0, result.getSalary());
        assertEquals(12345, result.getRegistration());
        assertEquals("40h", result.getWorkload());
        assertFalse(result.isManager());
        assertFalse(result.isSupportManager());
    }

    @Test
    @DisplayName("Should update existing Collaborator entity from CollaboratorRequestDTO")
    void shouldUpdateEntityFromRequestDTO() {
        // Arrange
        Collaborator existingCollaborator = new Collaborator();
        existingCollaborator.setName("Old Name");
        existingCollaborator.setRegistration(99999);
        existingCollaborator.setSalary(3000.0);

        CollaboratorRequestDTO updateDTO = new CollaboratorRequestDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setRegistration(12345);
        updateDTO.setSalary(6000.0);
        updateDTO.setPosition("Senior Engineer");
        updateDTO.setManager(true);
        updateDTO.setSupportManager(false);
        updateDTO.setEmail("updated@example.com");
        updateDTO.setPhone("+55 11 98765-4321");
        updateDTO.setAddress("New Address");
        updateDTO.setAdmissionDate(LocalDate.of(2025, 1, 1));
        updateDTO.setContractType("PJ");
        updateDTO.setWorkload("30h");
        updateDTO.setBank("001");
        updateDTO.setAgency("5678");
        updateDTO.setAccount("123456789012-3");
        updateDTO.setTypeAccount("Savings");
        updateDTO.setPix("updated@example.com");
        updateDTO.setWorkWallet("98765432109");
        updateDTO.setVoterRegistration("210987654321");
        updateDTO.setPIS("32630500000");
        updateDTO.setCNH("10987654321");
        updateDTO.setCPF("046.797.650-38");
        updateDTO.setRG("16.387.753-1");

        // Act
        collaboratorMapper.updateEntity(existingCollaborator, updateDTO);

        // Assert
        assertNotNull(existingCollaborator);
     // assertEquals(1L, existingCollaborator.getId()); // ID should not change
        assertEquals(12345, existingCollaborator.getRegistration());
        assertEquals(6000.0, existingCollaborator.getSalary());
        assertEquals("Senior Engineer", existingCollaborator.getPosition());
        assertTrue(existingCollaborator.isManager());
        assertFalse(existingCollaborator.isSupportManager());
    }

    @Test
    @DisplayName("Should handle null values in CollaboratorRequestDTO")
    void shouldHandleNullValuesInRequestDTO() {
        // Arrange
        CollaboratorRequestDTO nullDTO = new CollaboratorRequestDTO();
        nullDTO.setName("Test Collaborator");
        nullDTO.setRegistration(54321);
        nullDTO.setManager(false);
        nullDTO.setSupportManager(false);
        // Other fields are null

        // Act
        Collaborator result = collaboratorMapper.toEntity(nullDTO);

        // Assert
        assertNotNull(result);
        assertEquals(54321, result.getRegistration());
        assertFalse(result.isManager());
        assertFalse(result.isSupportManager());
    }

    @Test
    @DisplayName("Should handle null Collaborator entity")
    void shouldHandleNullEntity() {
        // Act
        CollaboratorResponseDTO result = collaboratorMapper.toResponse(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null CollaboratorRequestDTO")
    void shouldHandleNullRequestDTO() {
        // Act
        Collaborator result = collaboratorMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should map manager flag correctly")
    void shouldMapManagerFlagCorrectly() {
        // Arrange
        collaboratorRequestDTO.setManager(true);
        collaboratorRequestDTO.setSupportManager(false);

        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertTrue(result.isManager());
        assertFalse(result.isSupportManager());
    }

    @Test
    @DisplayName("Should map support manager flag correctly")
    void shouldMapSupportManagerFlagCorrectly() {
        // Arrange
        collaboratorRequestDTO.setManager(false);
        collaboratorRequestDTO.setSupportManager(true);

        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertFalse(result.isManager());
        assertTrue(result.isSupportManager());
    }

    @Test
    @DisplayName("Should map both manager flags as true")
    void shouldMapBothManagerFlagsAsTrue() {
        // Arrange
        collaboratorRequestDTO.setManager(true);
        collaboratorRequestDTO.setSupportManager(true);

        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertTrue(result.isManager());
        assertTrue(result.isSupportManager());
    }

    @Test
    @DisplayName("Should map dates correctly")
    void shouldMapDatesCorrectly() {
        // Arrange
        LocalDate specificBirthDate = LocalDate.of(1985, 3, 20);
        LocalDate specificAdmissionDate = LocalDate.of(2020, 6, 15);
        
        collaboratorRequestDTO.setBirthDate(specificBirthDate);
        collaboratorRequestDTO.setAdmissionDate(specificAdmissionDate);

        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertEquals(specificBirthDate, result.getBirthDate());
        assertEquals(specificAdmissionDate, result.getAdmissionDate());
    }

    /*@Test
    @DisplayName("Should not modify ID when updating entity")
    void shouldNotModifyIdWhenUpdatingEntity() {
        // Arrange
        Long originalId = 999L;
        Collaborator existingCollaborator = new Collaborator();
        existingCollaborator.setName("Original Name");

        // Act
        collaboratorMapper.updateEntity(existingCollaborator, collaboratorRequestDTO);

        // Assert
        assertEquals(originalId, existingCollaborator.getId());
    } */

    @Test
    @DisplayName("Should map personal information correctly")
    void shouldMapPersonalInformationCorrectly() {
        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertEquals("John Doe", result.getName());
        assertEquals(LocalDate.of(1990, 5, 15), result.getBirthDate());
        assertEquals("Single", result.getMaritalStatus());
        assertEquals("Brazilian", result.getNationality());
    }

    @Test
    @DisplayName("Should map professional information correctly")
    void shouldMapProfessionalInformationCorrectly() {
        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertEquals("Software Engineer", result.getPosition());
        assertEquals(LocalDate.of(2024, 1, 1), result.getAdmissionDate());
        assertEquals("CLT", result.getContractType());
        assertEquals(5000.0, result.getSalary());
        assertEquals(12345, result.getRegistration());
        assertEquals("40h", result.getWorkload());
    }

    @Test
    @DisplayName("Should map additional information correctly")
    void shouldMapAdditionalInformationCorrectly() {
        // Act
        Collaborator result = collaboratorMapper.toEntity(collaboratorRequestDTO);

        // Assert
        assertEquals("AB123456", result.getReservistCertificate());
        assertEquals("Jane Doe", result.getEmergencyContact());
        assertEquals("Bachelor's Degree", result.getEducation());
        assertEquals("Computer Science", result.getCourse());
        assertEquals("Excellent employee", result.getObservations());
    }

    @Test
    @DisplayName("Should preserve all field values when mapping to response")
    void shouldPreserveAllFieldValuesWhenMappingToResponse() {
        // Act
        CollaboratorResponseDTO result = collaboratorMapper.toResponse(collaborator);

        // Assert
        assertNotNull(result);
        assertEquals(collaborator.getName(), result.getName());
        assertEquals(collaborator.getRegistration(), result.getRegistration());
        assertEquals(collaborator.getPosition(), result.getPosition());
        assertEquals(collaborator.getSalary(), result.getSalary());
        assertEquals(collaborator.getAddress(), result.getAddress());
        assertEquals(collaborator.getWorkload(), result.getWorkload());
    }
}
