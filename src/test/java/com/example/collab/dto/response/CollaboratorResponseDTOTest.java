package com.example.collab.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CollaboratorResponseDTO Tests")
class CollaboratorResponseDTOTest {

    @Test
    @DisplayName("Should create response DTO with all fields")
    void shouldCreateResponseDTOWithAllFields() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        Long id = 1L;
        String name = "John Doe";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        String maritalStatus = "Single";
        String nationality = "Brazilian";
        String email = "john.doe@example.com";
        String phone = "11987654321";
        String address = "123 Main St";
        String position = "Software Engineer";
        Integer department = 101;
        boolean manager = false;
        boolean supportManager = false;
        LocalDate admissionDate = LocalDate.of(2024, 1, 1);
        String contractType = "CLT";
        Double salary = 5000.0;
        Integer registration = 12345;
        String workload = "40h";

        // Act
        dto.setId(id);
        dto.setName(name);
        dto.setBirthDate(birthDate);
        dto.setMaritalStatus(maritalStatus);
        dto.setNationality(nationality);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setAddress(address);
        dto.setPosition(position);
        dto.setDepartment(department);
        dto.setManager(manager);
        dto.setSupportManager(supportManager);
        dto.setAdmissionDate(admissionDate);
        dto.setContractType(contractType);
        dto.setSalary(salary);
        dto.setRegistration(registration);
        dto.setWorkload(workload);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(birthDate, dto.getBirthDate());
        assertEquals(maritalStatus, dto.getMaritalStatus());
        assertEquals(nationality, dto.getNationality());
        assertEquals(email, dto.getEmail());
        assertEquals(phone, dto.getPhone());
        assertEquals(address, dto.getAddress());
        assertEquals(position, dto.getPosition());
        assertEquals(department, dto.getDepartment());
        assertEquals(manager, dto.isManager());
        assertEquals(supportManager, dto.isSupportManager());
        assertEquals(admissionDate, dto.getAdmissionDate());
        assertEquals(contractType, dto.getContractType());
        assertEquals(salary, dto.getSalary());
        assertEquals(registration, dto.getRegistration());
        assertEquals(workload, dto.getWorkload());
    }

    @Test
    @DisplayName("Should handle null values")
    void shouldHandleNullValues() {
        // Arrange & Act
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        dto.setName("Test");
        dto.setRegistration(12345);

        // Assert
        assertNotNull(dto);
        assertEquals("Test", dto.getName());
        assertNull(dto.getId());
        assertNull(dto.getBirthDate());
        assertNull(dto.getEmail());
        assertNull(dto.getPhone());
        assertNull(dto.getAddress());
    }

    @Test
    @DisplayName("Should serialize dates correctly")
    void shouldSerializeDatesCorrectly() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        LocalDate birthDate = LocalDate.of(1990, 3, 15);
        LocalDate admissionDate = LocalDate.of(2024, 1, 1);

        // Act
        dto.setBirthDate(birthDate);
        dto.setAdmissionDate(admissionDate);

        // Assert
        assertEquals(birthDate, dto.getBirthDate());
        assertEquals(admissionDate, dto.getAdmissionDate());
        assertEquals("1990-03-15", dto.getBirthDate().toString());
        assertEquals("2024-01-01", dto.getAdmissionDate().toString());
    }

    @Test
    @DisplayName("Should set banking information")
    void shouldSetBankingInformation() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        String bank = "001";
        String agency = "1234";
        String account = "12345-6";
        String typeAccount = "Corrente";
        String pix = "john.doe@example.com";

        // Act
        dto.setBank(bank);
        dto.setAgency(agency);
        dto.setAccount(account);
        dto.setTypeAccount(typeAccount);
        dto.setPix(pix);

        // Assert
        assertEquals(bank, dto.getBank());
        assertEquals(agency, dto.getAgency());
        assertEquals(account, dto.getAccount());
        assertEquals(typeAccount, dto.getTypeAccount());
        assertEquals(pix, dto.getPix());
    }

    @Test
    @DisplayName("Should set document information")
    void shouldSetDocumentInformation() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        String workWallet = "12345678";
        String voterRegistration = "123456789012";
        String reservistCertificate = "AB123456";
        String pis = "12345678901";
        String cnh = "12345678901";
        String cpf = "12345678901";
        String rg = "123456789";

        // Act
        dto.setWorkWallet(workWallet);
        dto.setVoterRegistration(voterRegistration);
        dto.setReservistCertificate(reservistCertificate);
        dto.setPIS(pis);
        dto.setCNH(cnh);
        dto.setCPF(cpf);
        dto.setRG(rg);

        // Assert
        assertEquals(workWallet, dto.getWorkWallet());
        assertEquals(voterRegistration, dto.getVoterRegistration());
        assertEquals(reservistCertificate, dto.getReservistCertificate());
        assertEquals(pis, dto.getPIS());
        assertEquals(cnh, dto.getCNH());
        assertEquals(cpf, dto.getCPF());
        assertEquals(rg, dto.getRG());
    }

    @Test
    @DisplayName("Should set emergency contact information")
    void shouldSetEmergencyContactInformation() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        String emergencyContact = "Jane Doe";
        String phoneEmergency = "11987654322";

        // Act
        dto.setEmergencyContact(emergencyContact);
        dto.setPhoneEmergency(phoneEmergency);

        // Assert
        assertEquals(emergencyContact, dto.getEmergencyContact());
        assertEquals(phoneEmergency, dto.getPhoneEmergency());
    }

    @Test
    @DisplayName("Should set additional information")
    void shouldSetAdditionalInformation() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        String education = "Bachelor's Degree";
        String course = "Computer Science";
        String observations = "Excellent employee";

        // Act
        dto.setEducation(education);
        dto.setCourse(course);
        dto.setObservations(observations);

        // Assert
        assertEquals(education, dto.getEducation());
        assertEquals(course, dto.getCourse());
        assertEquals(observations, dto.getObservations());
    }

    @Test
    @DisplayName("Should handle manager flags correctly")
    void shouldHandleManagerFlagsCorrectly() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();

        // Act
        dto.setManager(true);
        dto.setSupportManager(false);

        // Assert
        assertTrue(dto.isManager());
        assertFalse(dto.isSupportManager());
    }

    @Test
    @DisplayName("Should handle both manager flags as true")
    void shouldHandleBothManagerFlagsAsTrue() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();

        // Act
        dto.setManager(true);
        dto.setSupportManager(true);

        // Assert
        assertTrue(dto.isManager());
        assertTrue(dto.isSupportManager());
    }

    @Test
    @DisplayName("Should be mutable after creation")
    void shouldBeMutableAfterCreation() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        dto.setName("Initial Name");
        dto.setRegistration(100);
        dto.setSalary(3000.0);

        // Act
        dto.setName("Updated Name");
        dto.setRegistration(200);
        dto.setSalary(5000.0);

        // Assert
        assertEquals("Updated Name", dto.getName());
        assertEquals(200, dto.getRegistration());
        assertEquals(5000.0, dto.getSalary());
    }

    @Test
    @DisplayName("Should maintain data integrity")
    void shouldMaintainDataIntegrity() {
        // Arrange
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        Integer originalRegistration = 12345;
        String originalName = "John Doe";
        Double originalSalary = 5000.0;

        // Act
        dto.setRegistration(originalRegistration);
        dto.setName(originalName);
        dto.setSalary(originalSalary);

        // Assert
        assertSame(originalRegistration, dto.getRegistration());
        assertSame(originalName, dto.getName());
        assertSame(originalSalary, dto.getSalary());
    }

    @Test
    @DisplayName("Should support complete collaborator profile")
    void shouldSupportCompleteCollaboratorProfile() {
        // Arrange & Act
        CollaboratorResponseDTO dto = new CollaboratorResponseDTO();
        dto.setId(1L);
        dto.setName("Complete Profile");
        dto.setRegistration(99999);
        dto.setPosition("Senior Engineer");
        dto.setDepartment(101);
        dto.setManager(true);
        dto.setSupportManager(true);
        dto.setSalary(10000.0);
        dto.setEmail("complete@example.com");
        dto.setPhone("11999999999");
        dto.setBank("001");
        dto.setAgency("9999");
        dto.setAccount("99999-9");
        dto.setCPF("99999999999");
        dto.setRG("999999999");

        // Assert - Verify all critical fields are set
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Complete Profile", dto.getName());
        assertEquals(99999, dto.getRegistration());
        assertEquals("Senior Engineer", dto.getPosition());
        assertEquals(101, dto.getDepartment());
        assertTrue(dto.isManager());
        assertTrue(dto.isSupportManager());
        assertEquals(10000.0, dto.getSalary());
    }
}
