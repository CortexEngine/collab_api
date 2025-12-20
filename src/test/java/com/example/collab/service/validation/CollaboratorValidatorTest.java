package com.example.collab.service.validation;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.document.*;
import com.example.collab.exception.business.InvalidCollaboratorException;
import com.example.collab.exception.domain.*;
import com.example.collab.repository.CollaboratorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("CollaboratorValidator Tests")
class CollaboratorValidatorTest {

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @InjectMocks
    private CollaboratorValidator collaboratorValidator;

    private Collaborator collaborator;

    @BeforeEach
    void setUp() {

        collaborator = new Collaborator();
        collaborator.setRegistration(12345);

    }

    @Test
    @DisplayName("Should validate new collaborator documents when all are unique")
    void shouldValidateNewCollaboratorDocumentsWhenAllAreUnique() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByCNH(any(CNH.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPIS(any(PIS.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByWorkWallet(any(WorkWallet.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByVoterRegistration(any(VoterRegistration.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorDocuments(
                "04679765038",
                "163877531",
                "12345678901",
                "32630500000",
                "12345678901",
                "123456789012"
        ));

        verify(collaboratorRepository, times(1)).findByCPF(any(CPF.class));
        verify(collaboratorRepository, times(1)).findByRG(any(RG.class));
        verify(collaboratorRepository, times(1)).findByCNH(any(CNH.class));
        verify(collaboratorRepository, times(1)).findByPIS(any(PIS.class));
        verify(collaboratorRepository, times(1)).findByWorkWallet(any(WorkWallet.class));
        verify(collaboratorRepository, times(1)).findByVoterRegistration(any(VoterRegistration.class));

    }

    @Test
    @DisplayName("Should throw exception when CPF already exists")
    void shouldThrowExceptionWhenCPFAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedCPFException exception = assertThrows(DuplicatedCPFException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", null, null, null, null, null
                ));

        assertEquals("CPF already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByCPF(any(CPF.class));

    }

    @Test
    @DisplayName("Should throw exception when RG already exists")
    void shouldThrowExceptionWhenRGAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedRGException exception = assertThrows(DuplicatedRGException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", "163877531", null, null, null, null
                ));

        assertEquals("RG already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByRG(any(RG.class));

    }

    @Test
    @DisplayName("Should throw exception when CNH already exists")
    void shouldThrowExceptionWhenCNHAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByCNH(any(CNH.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedCNHException exception = assertThrows(DuplicatedCNHException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", "163877531", "12345678901", null, null, null
                ));

        assertEquals("CNH already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByCNH(any(CNH.class));

    }

    @Test
    @DisplayName("Should throw exception when PIS already exists")
    void shouldThrowExceptionWhenPISAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByCNH(any(CNH.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPIS(any(PIS.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedPISException exception = assertThrows(DuplicatedPISException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", "163877531", "12345678901", "32630500000", null, null
                ));

        assertEquals("PIS already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByPIS(any(PIS.class));

    }

    @Test
    @DisplayName("Should throw exception when WorkWallet already exists")
    void shouldThrowExceptionWhenWorkWalletAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByCNH(any(CNH.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPIS(any(PIS.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByWorkWallet(any(WorkWallet.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedWorkWalletException exception = assertThrows(DuplicatedWorkWalletException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", "163877531", "12345678901", "32630500000","12345678901", null
                ));

        assertEquals("Work Wallet already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByWorkWallet(any(WorkWallet.class));

    }

    @Test
    @DisplayName("Should throw exception when VoterRegistration already exists")
    void shouldThrowExceptionWhenVoterRegistrationAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByCPF(any(CPF.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByRG(any(RG.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByCNH(any(CNH.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPIS(any(PIS.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByWorkWallet(any(WorkWallet.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByVoterRegistration(any(VoterRegistration.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedVoteRegistrationException exception = assertThrows(DuplicatedVoteRegistrationException.class,
                () -> collaboratorValidator.validateNewCollaboratorDocuments(
                        "04679765038", "163877531", "12345678901", "32630500000","12345678901", "123456789012"
                ));

        assertEquals("Vote Registration Title already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByVoterRegistration(any(VoterRegistration.class));

    }

    @Test
    @DisplayName("Should not validate when documents are null")
    void shouldNotValidateWhenDocumentsAreNull() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorDocuments(
                null, null, null, null, null, null
        ));

        verify(collaboratorRepository, never()).findByCPF(any());
        verify(collaboratorRepository, never()).findByRG(any());
        verify(collaboratorRepository, never()).findByCNH(any());
        verify(collaboratorRepository, never()).findByPIS(any());
        verify(collaboratorRepository, never()).findByWorkWallet(any());
        verify(collaboratorRepository, never()).findByVoterRegistration(any());

    }

    @Test
    @DisplayName("Should not validate when documents are blank")
    void shouldNotValidateWhenDocumentsAreBlank() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorDocuments(
                "", "  ", "", "   ", "", "  "
        ));

        verify(collaboratorRepository, never()).findByCPF(any());
        verify(collaboratorRepository, never()).findByRG(any());
        verify(collaboratorRepository, never()).findByCNH(any());
        verify(collaboratorRepository, never()).findByPIS(any());
        verify(collaboratorRepository, never()).findByWorkWallet(any());
        verify(collaboratorRepository, never()).findByVoterRegistration(any());

    }

    @Test
    @DisplayName("Should validate new collaborator bank when all are unique")
    void shouldValidateNewCollaboratorBankWhenAllAreUnique() {

        // Arrange
        when(collaboratorRepository.findByAccount(any(Account.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPix(any(PIX.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorBank(
                "123456789012-3",
                "test@example.com"
        ));

        verify(collaboratorRepository, times(1)).findByAccount(any(Account.class));
        verify(collaboratorRepository, times(1)).findByPix(any(PIX.class));

    }

    @Test
    @DisplayName("Should throw exception when Account already exists")
    void shouldThrowExceptionWhenAccountAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByAccount(any(Account.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedAccountException exception = assertThrows(DuplicatedAccountException.class,
                () -> collaboratorValidator.validateNewCollaboratorBank("123456-7", null));

        assertEquals("Account already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByAccount(any(Account.class));

    }

    @Test
    @DisplayName("Should throw exception when PIX already exists")
    void shouldThrowExceptionWhenPIXAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByAccount(any(Account.class))).thenReturn(Optional.empty());
        when(collaboratorRepository.findByPix(any(PIX.class))).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedPixException exception = assertThrows(DuplicatedPixException.class,
                () -> collaboratorValidator.validateNewCollaboratorBank("123456789012-3", "test@example.com"));

        assertEquals("Pix already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByPix(any(PIX.class));

    }

    @Test
    @DisplayName("Should not validate bank when values are null")
    void shouldNotValidateBankWhenValuesAreNull() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorBank(null, null));

        verify(collaboratorRepository, never()).findByAccount(any());
        verify(collaboratorRepository, never()).findByPix(any());

    }

    @Test
    @DisplayName("Should not validate bank when values are blank")
    void shouldNotValidateBankWhenValuesAreBlank() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorBank("", "  "));

        verify(collaboratorRepository, never()).findByAccount(any());
        verify(collaboratorRepository, never()).findByPix(any());

    }

    @Test
    @DisplayName("Should validate new collaborator registration when it is unique")
    void shouldValidateNewCollaboratorRegistrationWhenItIsUnique() {

        // Arrange
        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorData(12345));

        verify(collaboratorRepository, times(1)).findByRegistration(12345);

    }

    @Test
    @DisplayName("Should throw exception when registration already exists")
    void shouldThrowExceptionWhenRegistrationAlreadyExists() {

        // Arrange
        when(collaboratorRepository.findByRegistration(12345)).thenReturn(Optional.of(collaborator));

        // Act & Assert
        DuplicatedAccountException exception = assertThrows(DuplicatedAccountException.class,
                () -> collaboratorValidator.validateNewCollaboratorData(12345));

        assertEquals("Registration already exists", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByRegistration(12345);

    }

    @Test
    @DisplayName("Should not validate when registration is null")
    void shouldNotValidateWhenRegistrationIsNull() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateNewCollaboratorData(null));

        verify(collaboratorRepository, never()).findByRegistration(any());

    }

    @Test
    @DisplayName("Should validate collaborator when not manager and support manager")
    void shouldValidateCollaboratorWhenNotManagerAndSupportManager() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndSupportManager(12345, true)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateCollaboratorManager(12345));

        verify(collaboratorRepository, times(1)).findByRegistrationAndSupportManager(12345, true);

    }

    @Test
    @DisplayName("Should throw exception when collaborator is manager and support manager")
    void shouldThrowExceptionWhenCollaboratorIsManagerAndSupportManager() {

        // Arrange
        when(collaboratorRepository.findByRegistrationAndSupportManager(12345, true)).thenReturn(Optional.of(collaborator));

        // Act & Assert
        InvalidCollaboratorException exception = assertThrows(InvalidCollaboratorException.class,
                () -> collaboratorValidator.validateCollaboratorManager(12345));

        assertEquals("Collaborator not do Manager and Suppor Manager in same time", exception.getMessage());
        verify(collaboratorRepository, times(1)).findByRegistrationAndSupportManager(12345, true);

    }

    @Test
    @DisplayName("Should not validate manager when registration is null")
    void shouldNotValidateManagerWhenRegistrationIsNull() {

        // Act & Assert
        assertDoesNotThrow(() -> collaboratorValidator.validateCollaboratorManager(null));

        verify(collaboratorRepository, never()).findByRegistrationAndSupportManager(any(), anyBoolean());

    }
}
