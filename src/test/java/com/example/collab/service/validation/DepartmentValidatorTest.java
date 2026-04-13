package com.example.collab.service.validation;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.exception.domain.*;
import com.example.collab.exception.business.*;
import com.example.collab.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DepartmentValidator Tests")
class DepartmentValidatorTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @InjectMocks
    private DepartmentValidator departmentValidator;

    private Collaborator manager;
    private Collaborator supportManager;
    private Collaborator teamMember;

    @BeforeEach
    void setUp() {

        manager = new Collaborator();
        manager.setRegistration(1001);
        manager.setManager(true);
        manager.setName("Manager User");

        supportManager = new Collaborator();
        supportManager.setRegistration(1002);
        supportManager.setSupportManager(true);
        supportManager.setName("Support Manager User");

        teamMember = new Collaborator();
        teamMember.setRegistration(2001);
        teamMember.setName("Team Member User");

    }

    @Test
    @DisplayName("Should validate department manager successfully")
    void shouldValidateDepartmentManagerSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndManager(1001, true)).thenReturn(Optional.of(manager));

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentManager(1001));
        verify(collaboratorRepository).findByRegistrationAndManager(1001, true);

    }

    @Test
    @DisplayName("Should throw InvalidManagerException when collaborator is not a manager")
    void shouldThrowInvalidManagerExceptionWhenCollaboratorIsNotManager() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndManager(2001, true)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidManagerException.class, () -> departmentValidator.validateDepartmentManager(2001));

    }

    @Test
    @DisplayName("Should validate single support manager successfully")
    void shouldValidateSingleSupportManagerSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndSupportManager(1002, true)).thenReturn(Optional.of(supportManager));

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentSupportManager(1002));
        verify(collaboratorRepository).findByRegistrationAndSupportManager(1002, true);

    }

    @Test
    @DisplayName("Should validate multiple support managers successfully")
    void shouldValidateMultipleSupportManagersSuccessfully() {

        // Arrange
        List<Integer> registrations = Arrays.asList(1002, 1003);
        when(collaboratorRepository.findByRegistrationAndSupportManager(1002, true)).thenReturn(Optional.of(supportManager));
        when(collaboratorRepository.findByRegistrationAndSupportManager(1003, true)).thenReturn(Optional.of(supportManager));

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentSupportManager(registrations));
        verify(collaboratorRepository).findByRegistrationAndSupportManager(1002, true);
        verify(collaboratorRepository).findByRegistrationAndSupportManager(1003, true);

    }

    @Test
    @DisplayName("Should throw InvalidSupportManagerException when collaborator is not support manager")
    void shouldThrowInvalidSupportManagerExceptionWhenCollaboratorIsNotSupportManager() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndSupportManager(2001, true)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidSupportManagerException.class, () -> departmentValidator.validateDepartmentSupportManager(2001));

    }

    @Test
    @DisplayName("Should throw BadRequestException when support manager is null")
    void shouldThrowBadRequestExceptionWhenSupportManagerIsNull() {

        // Act & Assert
        assertThrows(BadRequestException.class, () -> departmentValidator.validateDepartmentSupportManager(null));

    }

    @Test
    @DisplayName("Should validate department name successfully when name does not exist")
    void shouldValidateDepartmentNameSuccessfully() {

        // Arrange
        when(departmentRepository.findByName("Engineering")).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentName("Engineering"));
        verify(departmentRepository).findByName("Engineering");

    }

    @Test
    @DisplayName("Should throw DuplicatedNameDepartmentException when name already exists")
    void shouldThrowDuplicatedNameDepartmentExceptionWhenNameExists() {

        // Arrange
        when(departmentRepository.findByName("Engineering")).thenReturn(Optional.of(new com.example.collab.domain.model.Department()));

        // Act & Assert
        assertThrows(DuplicatedNameDepartmentException.class, () -> departmentValidator.validateDepartmentName("Engineering"));

    }

    @Test
    @DisplayName("Should validate department number successfully when number does not exist")
    void shouldValidateDepartmentNumberSuccessfully() {

        // Arrange
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentNumber(101));
        verify(departmentRepository).findByNumber(101);

    }

    @Test
    @DisplayName("Should throw DuplicatedNumberDepartmentException when number already exists")
    void shouldThrowDuplicatedNumberDepartmentExceptionWhenNumberExists() {

        // Arrange
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.of(new com.example.collab.domain.model.Department()));

        // Act & Assert
        assertThrows(DuplicatedNumberDepartmentException.class, () -> departmentValidator.validateDepartmentNumber(101));

    }

    @Test
    @DisplayName("Should validate single team member successfully")
    void shouldValidateSingleTeamMemberSuccessfully() {

        // Arrange
        when(collaboratorRepository.findByRegistration(2001)).thenReturn(Optional.of(teamMember));

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentMembers(2001));
        verify(collaboratorRepository).findByRegistration(2001);

    }

    @Test
    @DisplayName("Should validate multiple team members successfully")
    void shouldValidateMultipleTeamMembersSuccessfully() {

        // Arrange
        List<Integer> registrations = Arrays.asList(2001, 2002);
        when(collaboratorRepository.findByRegistration(2001)).thenReturn(Optional.of(teamMember));
        when(collaboratorRepository.findByRegistration(2002)).thenReturn(Optional.of(teamMember));

        // Act & Assert
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentMembers(registrations));
        verify(collaboratorRepository).findByRegistration(2001);
        verify(collaboratorRepository).findByRegistration(2002);

    }

    @Test
    @DisplayName("Should throw BadRequestException when team member registration is null")
    void shouldThrowBadRequestExceptionWhenTeamMemberIsNull() {

        // Act & Assert
        assertThrows(BadRequestException.class, () -> departmentValidator.validateDepartmentMembers(null));

    }

    @Test
    @DisplayName("Should validate all manager types in a complete flow")
    void shouldValidateAllManagerTypesInCompleteFlow() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndManager(1001, true)).thenReturn(Optional.of(manager));
        when(collaboratorRepository.findByRegistrationAndSupportManager(1002, true)).thenReturn(Optional.of(supportManager));
        when(collaboratorRepository.findByRegistration(2001)).thenReturn(Optional.of(teamMember));
        when(departmentRepository.findByName("Engineering")).thenReturn(Optional.empty());
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.empty());

        // Act & Assert - All validations should pass
        assertDoesNotThrow(() -> {
            departmentValidator.validateDepartmentName("Engineering");
            departmentValidator.validateDepartmentNumber(101);
            departmentValidator.validateDepartmentManager(1001);
            departmentValidator.validateDepartmentSupportManager(1002);
            departmentValidator.validateDepartmentMembers(2001);
        });
        
    }
}
