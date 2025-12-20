package com.example.collab.service;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.domain.valueobject.banking.Bank;
import com.example.collab.domain.valueobject.document.CPF;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;
import com.example.collab.exception.business.*;
import com.example.collab.exception.resource.NotFoundCollaboratorException;
import com.example.collab.mapper.CollaboratorMapper;
import com.example.collab.repository.CollaboratorRepository;
import com.example.collab.service.validation.CollaboratorValidator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    private CollaboratorRequestDTO collaboratorRequestDTO;
    private Collaborator collaborator;
    private CollaboratorResponseDTO collaboratorResponseDTO;

    @BeforeEach
    void setUp() {

        // Setup request DTO
        collaboratorRequestDTO = new CollaboratorRequestDTO();
        collaboratorRequestDTO.setName("John Doe");
        collaboratorRequestDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        collaboratorRequestDTO.setMaritalStatus("Single");
        collaboratorRequestDTO.setNationality("Brazilian");
        collaboratorRequestDTO.setEmail("john.doe@example.com");
        collaboratorRequestDTO.setPhone("11987654321");
        collaboratorRequestDTO.setAddress("123 Main St");
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
        collaboratorRequestDTO.setAccount("12345-6");
        collaboratorRequestDTO.setTypeAccount("Corrente");
        collaboratorRequestDTO.setPix("john.doe@example.com");
        collaboratorRequestDTO.setWorkWallet("12345678");
        collaboratorRequestDTO.setVoterRegistration("123456789012");
        collaboratorRequestDTO.setPIS("12345678901");
        collaboratorRequestDTO.setCNH("12345678901");
        collaboratorRequestDTO.setCPF("12345678901");
        collaboratorRequestDTO.setRG("123456789");

        // Setup entity
        collaborator = new Collaborator();
        collaborator.setName("John Doe");
        collaborator.setRegistration(12345);
        collaborator.setPosition("Software Engineer");
        collaborator.setSalary(5000.0);
        collaborator.setManager(false);
        collaborator.setSupportManager(false);

        // Setup response DTO
        collaboratorResponseDTO = new CollaboratorResponseDTO();
        collaboratorResponseDTO.setId(1L);
        collaboratorResponseDTO.setName("John Doe");
        collaboratorResponseDTO.setRegistration(12345);
        collaboratorResponseDTO.setPosition("Software Engineer");
        collaboratorResponseDTO.setSalary(5000.0);
        collaboratorResponseDTO.setManager(false);
        collaboratorResponseDTO.setSupportManager(false);

    }

    @Test
    @DisplayName("Should create collaborator successfully")
    void shouldCreateCollaboratorSuccessfully() {

        // Arrange
        doNothing().when(collaboratorValidator).validateNewCollaboratorDocuments(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        doNothing().when(collaboratorValidator).validateNewCollaboratorBank(anyString(), anyString());
        doNothing().when(collaboratorValidator).validateNewCollaboratorData(anyInt());
        doNothing().when(collaboratorValidator).validateCollaboratorManager(anyInt());
        
        when(collaboratorMapper.toEntity(any(CollaboratorRequestDTO.class))).thenReturn(collaborator);
        when(collaboratorRepository.save(any(Collaborator.class))).thenReturn(collaborator);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        CollaboratorResponseDTO result = collaboratorService.createCollaborator(collaboratorRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(12345, result.getRegistration());
        verify(collaboratorValidator).validateNewCollaboratorDocuments(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        verify(collaboratorValidator).validateNewCollaboratorBank(anyString(), anyString());
        verify(collaboratorRepository).save(collaborator);

    }

    @Test
    @DisplayName("Should throw InvalidCollaboratorException when mapper returns null")
    void shouldThrowInvalidCollaboratorExceptionWhenMapperReturnsNull() {

        // Arrange
        doNothing().when(collaboratorValidator).validateNewCollaboratorDocuments(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        doNothing().when(collaboratorValidator).validateNewCollaboratorBank(anyString(), anyString());
        doNothing().when(collaboratorValidator).validateNewCollaboratorData(anyInt());
        doNothing().when(collaboratorValidator).validateCollaboratorManager(anyInt());
        
        when(collaboratorMapper.toEntity(any(CollaboratorRequestDTO.class))).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidCollaboratorException.class, () -> collaboratorService.createCollaborator(collaboratorRequestDTO));

    }

    @Test
    @DisplayName("Should get all collaborators successfully")
    void shouldGetAllCollaboratorsSuccessfully() {

        // Arrange
        List<Collaborator> collaborators = Arrays.asList(collaborator);
        when(collaboratorRepository.findAll()).thenReturn(collaborators);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        List<CollaboratorResponseDTO> result = collaboratorService.getAllCollaborators();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(collaboratorRepository).findAll();

    }

    @Test
    @DisplayName("Should throw NotFoundCollaboratorException when no collaborators found")
    void shouldThrowNotFoundCollaboratorExceptionWhenNoCollaboratorsFound() {

        // Arrange
        when(collaboratorRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NotFoundCollaboratorException.class, () -> collaboratorService.getAllCollaborators());

    }

    @Test
    @DisplayName("Should get collaborator by registration successfully")
    void shouldGetCollaboratorByRegistrationSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.of(collaborator));
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        CollaboratorResponseDTO result = collaboratorService.getCollaboratorByRegistration(12345);

        // Assert
        assertNotNull(result);
        assertEquals(12345, result.getRegistration());
        verify(collaboratorRepository).findByRegistration(12345);

    }

    @Test
    @DisplayName("Should throw NotFoundCollaboratorException when registration not found")
    void shouldThrowNotFoundCollaboratorExceptionWhenRegistrationNotFound() {

        // Arrange
        when(collaboratorRepository.findByRegistration(99999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundCollaboratorException.class, () -> collaboratorService.getCollaboratorByRegistration(99999));

    }

    @Test
    @DisplayName("Should get collaborator by CPF successfully")
    void shouldGetCollaboratorByCPFSuccessfully() {

        // Arrange
        new CPF("87192308005");
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.of(collaborator));
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        CollaboratorResponseDTO result = collaboratorService.getCollaboratorByCPF("87192308005");

        // Assert
        assertNotNull(result);
        verify(collaboratorRepository).findByCPF(any(CPF.class));

    }

    @Test
    @DisplayName("Should throw NotFoundCollaboratorException when CPF not found")
    void shouldThrowNotFoundCollaboratorExceptionWhenCPFNotFound() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundCollaboratorException.class, () -> collaboratorService.getCollaboratorByCPF("87192308005"));

    }

    @Test
    @DisplayName("Should get collaborators by name successfully")
    void shouldGetCollaboratorsByNameSuccessfully() {

        // Arrange
        List<Collaborator> collaborators = Arrays.asList(collaborator);
        when(collaboratorRepository.findByName("John Doe")).thenReturn(collaborators);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        List<CollaboratorResponseDTO> result = collaboratorService.getCollaboratorByName("John Doe");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(collaboratorRepository).findByName("John Doe");

    }

    @Test
    @DisplayName("Should throw NotFoundCollaboratorException when name not found")
    void shouldThrowNotFoundCollaboratorExceptionWhenNameNotFound() {

        // Arrange
        when(collaboratorRepository.findByName("NonExistent")).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NotFoundCollaboratorException.class, () -> collaboratorService.getCollaboratorByName("NonExistent"));

    }

    @Test
    @DisplayName("Should get collaborators by position successfully")
    void shouldGetCollaboratorsByPositionSuccessfully() {

        // Arrange
        List<Collaborator> collaborators = Arrays.asList(collaborator);
        when(collaboratorRepository.findByPosition("Software Engineer")).thenReturn(collaborators);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        List<CollaboratorResponseDTO> result = collaboratorService.getCollaboratorByPosition("Software Engineer");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Software Engineer", result.get(0).getPosition());
        verify(collaboratorRepository).findByPosition("Software Engineer");

    }

    @Test
    @DisplayName("Should get collaborators by bank successfully")
    void shouldGetCollaboratorsByBankSuccessfully() {

        // Arrange
        List<Collaborator> collaborators = Arrays.asList(collaborator);
        when(collaboratorRepository.findByBank(any(Bank.class))).thenReturn(collaborators);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        List<CollaboratorResponseDTO> result = collaboratorService.getCollaboratorByBank("001");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(collaboratorRepository).findByBank(any(Bank.class));

    }

    @Test
    @DisplayName("Should delete collaborator by registration successfully")
    void shouldDeleteCollaboratorByRegistrationSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.of(collaborator));
        doNothing().when(collaboratorRepository).delete(any(Collaborator.class));

        // Act
        String result = collaboratorService.deleteCollaboratorByRegistration(12345);

        // Assert
        assertEquals("John Doe", result);
        verify(collaboratorRepository).findByRegistration(12345);
        verify(collaboratorRepository).delete(collaborator);

    }

    @Test
    @DisplayName("Should throw BadRequestException when deleting non-existent collaborator")
    void shouldThrowBadRequestExceptionWhenDeletingNonExistent() {

        // Arrange
        when(collaboratorRepository.findByRegistration(99999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> collaboratorService.deleteCollaboratorByRegistration(99999));

    }

    @Test
    @DisplayName("Should delete collaborator by CPF successfully")
    void shouldDeleteCollaboratorByCPFSuccessfully() {

        // Arrange
        CPF cpf = new CPF("04293290087");
        collaborator.setCPF(cpf);
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.of(collaborator));
        doNothing().when(collaboratorRepository).delete(any(Collaborator.class));

        // Act
        CPF result = collaboratorService.deleteCollaboratorByCPF(cpf);

        // Assert
        assertNotNull(result);
        verify(collaboratorRepository).findByCPF(cpf);
        verify(collaboratorRepository).delete(collaborator);

    }

    @Test
    @DisplayName("Should update collaborator successfully")
    void shouldUpdateCollaboratorSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.of(collaborator));
        doNothing().when(collaboratorValidator).validateNewCollaboratorData(anyInt());
        doNothing().when(collaboratorValidator).validateCollaboratorManager(anyInt());
        doNothing().when(collaboratorMapper).updateEntity(any(Collaborator.class), any(CollaboratorRequestDTO.class));
        when(collaboratorRepository.save(any(Collaborator.class))).thenReturn(collaborator);
        when(collaboratorMapper.toResponse(any(Collaborator.class))).thenReturn(collaboratorResponseDTO);

        // Act
        CollaboratorResponseDTO result = collaboratorService.updateCollaborator(12345, collaboratorRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(collaboratorRepository).findByRegistration(12345);
        verify(collaboratorRepository).save(collaborator);

    }

    @Test
    @DisplayName("Should throw BadRequestException when updating non-existent collaborator")
    void shouldThrowBadRequestExceptionWhenUpdatingNonExistent() {

        // Arrange
        when(collaboratorRepository.findByRegistration(99999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> collaboratorService.updateCollaborator(99999, collaboratorRequestDTO));

    }
}
