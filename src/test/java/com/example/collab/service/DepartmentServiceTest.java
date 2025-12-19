package com.example.collab.service;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.exception.business.BadRequestException;
import com.example.collab.exception.resource.DepartmentNotFoundException;
import com.example.collab.mapper.DepartmentMapper;
import com.example.collab.repository.DepartmentRepository;
import com.example.collab.service.validation.DepartmentValidator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;




@ExtendWith(MockitoExtension.class)
@DisplayName("DepartmentService Tests")
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentValidator departmentValidator;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private DepartmentRequestDTO departmentRequestDTO;
    private Department department;
    private DepartmentResponseDTO departmentResponseDTO;

    @BeforeEach
    void setUp() {

        // Setup request DTO
        departmentRequestDTO = new DepartmentRequestDTO();
        departmentRequestDTO.setName("Engineering");
        departmentRequestDTO.setNumber(101);
        departmentRequestDTO.setInitialDate(LocalDate.of(2024, 1, 1));
        departmentRequestDTO.setEndDate(LocalDate.of(2025, 12, 31));
        departmentRequestDTO.setManagerRegistration(1001);
        departmentRequestDTO.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        departmentRequestDTO.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

        // Setup entity
        department = new Department();
        department.setName("Engineering");
        department.setNumber(101);
        department.setInitialDate(LocalDate.of(2024, 1, 1));
        department.setEndDate(LocalDate.of(2025, 12, 31));
        department.setManagerRegistration(1001);
        department.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        department.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

        // Setup response DTO
        departmentResponseDTO = new DepartmentResponseDTO();
        departmentResponseDTO.setName("Engineering");
        departmentResponseDTO.setNumber(101);
        departmentResponseDTO.setInitialDate(LocalDate.of(2024, 1, 1));
        departmentResponseDTO.setEndDate(LocalDate.of(2025, 12, 31));
        departmentResponseDTO.setManagerRegistration(1001);
        departmentResponseDTO.setManagerSupportRegistration(Arrays.asList(1002, 1003));
        departmentResponseDTO.setTeamMembersRegistration(Arrays.asList(2001, 2002, 2003));

    }

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartmentSuccessfully() {

        // Arrange
        doNothing().when(departmentValidator).validateDepartmentName(anyString());
        doNothing().when(departmentValidator).validateDepartmentNumber(anyInt());
        doNothing().when(departmentValidator).validateDepartmentManager(anyInt());
        doNothing().when(departmentValidator).validateDepartmentSupportManager(anyInt());
        doNothing().when(departmentValidator).validateDepartmentMembers(anyInt());
        
        when(departmentMapper.toEntity(any(DepartmentRequestDTO.class))).thenReturn(department);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        DepartmentResponseDTO result = departmentService.createDepartment(departmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        assertEquals(101, result.getNumber());
        verify(departmentValidator).validateDepartmentName("Engineering");
        verify(departmentValidator).validateDepartmentNumber(101);
        verify(departmentValidator).validateDepartmentManager(1001);
        verify(departmentRepository).save(department);

    }

    @Test
    @DisplayName("Should throw BadRequestException when mapper returns null")
    void shouldThrowBadRequestExceptionWhenMapperReturnsNull() {

        // Arrange
        doNothing().when(departmentValidator).validateDepartmentName(anyString());
        doNothing().when(departmentValidator).validateDepartmentNumber(anyInt());
        doNothing().when(departmentValidator).validateDepartmentManager(anyInt());
        doNothing().when(departmentValidator).validateDepartmentSupportManager(anyInt());
        doNothing().when(departmentValidator).validateDepartmentMembers(anyInt());
        
        when(departmentMapper.toEntity(any(DepartmentRequestDTO.class))).thenReturn(null);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> 
            departmentService.createDepartment(departmentRequestDTO)
        );

    }

    @Test
    @DisplayName("Should get all departments successfully")
    void shouldGetAllDepartmentsSuccessfully() {

        // Arrange
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findAll()).thenReturn(departments);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        List<DepartmentResponseDTO> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getName());
        verify(departmentRepository).findAll();

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when no departments found")
    void shouldThrowDepartmentNotFoundExceptionWhenNoDepartmentsFound() {

        // Arrange
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getAllDepartments());

    }

    @Test
    @DisplayName("Should get department by number successfully")
    void shouldGetDepartmentByNumberSuccessfully() {

        // Arrange
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.of(department));
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        DepartmentResponseDTO result = departmentService.getDepartmentByNumber(101);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getNumber());
        verify(departmentRepository).findByNumber(101);

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when department number not found")
    void shouldThrowDepartmentNotFoundExceptionWhenNumberNotFound() {

        // Arrange
        when(departmentRepository.findByNumber(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentByNumber(999));

    }

    @Test
    @DisplayName("Should get department by name successfully")
    void shouldGetDepartmentByNameSuccessfully() {

        // Arrange
        when(departmentRepository.findByName("Engineering")).thenReturn(Optional.of(department));
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        DepartmentResponseDTO result = departmentService.getDepartmentByName("Engineering");

        // Assert
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository).findByName("Engineering");

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when department name not found")
    void shouldThrowDepartmentNotFoundExceptionWhenNameNotFound() {

        // Arrange
        when(departmentRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentByName("NonExistent"));

    }

    @Test
    @DisplayName("Should get departments by manager registration successfully")
    void shouldGetDepartmentsByManagerRegistrationSuccessfully() {

        // Arrange
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findByManagerRegistration(1001)).thenReturn(departments);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        List<DepartmentResponseDTO> result = departmentService.getDepartmentsByManagerRegistration(1001);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1001, result.get(0).getManagerRegistration());
        verify(departmentRepository).findByManagerRegistration(1001);

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when no departments found for manager")
    void shouldThrowDepartmentNotFoundExceptionWhenNoManagerDepartments() {

        // Arrange
        when(departmentRepository.findByManagerRegistration(9999)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentsByManagerRegistration(9999));

    }

    @Test
    @DisplayName("Should get departments by support manager registration successfully")
    void shouldGetDepartmentsBySupportManagerRegistrationSuccessfully() {

        // Arrange
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findByManagerSupportRegistrationContains(1002)).thenReturn(departments);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        List<DepartmentResponseDTO> result = departmentService.getDepartmentsBySupportManagerRegistration(1002);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentRepository).findByManagerSupportRegistrationContains(1002);

    }

    @Test
    @DisplayName("Should get departments by team member registration successfully")
    void shouldGetDepartmentsByTeamMemberRegistrationSuccessfully() {

        // Arrange
        List<Integer> registrations = Arrays.asList(2001, 2002);
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findByTeamMembersRegistrationIn(registrations)).thenReturn(departments);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        List<DepartmentResponseDTO> result = departmentService.getDepartmentsByTeamMemberRegistration(registrations);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentRepository).findByTeamMembersRegistrationIn(registrations);

    }

    @Test
    @DisplayName("Should delete department by number successfully")
    void shouldDeleteDepartmentByNumberSuccessfully() {

        // Arrange
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));

        // Act
        Integer result = departmentService.deleteDepartmentByNumber(101);

        // Assert
        assertEquals(101, result);
        verify(departmentRepository).findByNumber(101);
        verify(departmentRepository).delete(department);

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when deleting non-existent department")
    void shouldThrowDepartmentNotFoundExceptionWhenDeletingNonExistent() {

        // Arrange
        when(departmentRepository.findByNumber(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartmentByNumber(999));

    }

    @Test
    @DisplayName("Should update department successfully")
    void shouldUpdateDepartmentSuccessfully() {

        // Arrange
        when(departmentRepository.findByNumber(101)).thenReturn(Optional.of(department));
        doNothing().when(departmentValidator).validateDepartmentNumber(anyInt());
        doNothing().when(departmentValidator).validateDepartmentName(anyString());
        doNothing().when(departmentValidator).validateDepartmentManager(anyInt());
        doNothing().when(departmentValidator).validateDepartmentSupportManager(any());
        doNothing().when(departmentValidator).validateDepartmentMembers(any());
        doNothing().when(departmentMapper).updateEntity(any(Department.class), any(DepartmentRequestDTO.class));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.toResponse(any(Department.class))).thenReturn(departmentResponseDTO);

        // Act
        DepartmentResponseDTO result = departmentService.updateDepartment(101, departmentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository).findByNumber(101);
        verify(departmentRepository).save(department);

    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when updating non-existent department")
    void shouldThrowDepartmentNotFoundExceptionWhenUpdatingNonExistent() {

        // Arrange
        when(departmentRepository.findByNumber(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(999, departmentRequestDTO));

    }
}
