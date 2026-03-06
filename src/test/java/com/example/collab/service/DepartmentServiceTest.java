package com.example.collab.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.exception.resource.DepartmentNotFoundException;
import com.example.collab.mapper.DepartmentMapper;
import com.example.collab.repository.DepartmentRepository;
import com.example.collab.service.validation.DepartmentValidator;

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

    @Test
    @DisplayName("Should create department successfully")
    void shouldCreateDepartmentSuccessfully() {
        DepartmentRequestDTO request = new DepartmentRequestDTO(
            "Engineering",
            101,
            LocalDate.now().minusDays(1),
            LocalDate.now().plusDays(10),
            1001,
            List.of(1002),
            List.of(2001)
        );

        Department department = new Department();
        department.setName("Engineering");
        department.setNumber(101);

        DepartmentResponseDTO response = new DepartmentResponseDTO(
            "Engineering",
            101,
            LocalDate.now().minusDays(1),
            LocalDate.now().plusDays(10),
            1001,
            List.of(1002),
            List.of(2001)
        );

        when(departmentMapper.toEntity(request)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.toResponse(department)).thenReturn(response);

        DepartmentResponseDTO result = departmentService.createDepartment(request);

        assertNotNull(result);
        assertEquals("Engineering", result.name());
        verify(departmentRepository).save(department);
    }

    @Test
    @DisplayName("Should get department by number")
    void shouldGetDepartmentByNumber() {
        Department department = new Department();
        department.setNumber(101);

        DepartmentResponseDTO response = new DepartmentResponseDTO(
            "Engineering",
            101,
            null,
            null,
            null,
            null,
            null
        );

        when(departmentRepository.findByNumber(101)).thenReturn(Optional.of(department));
        when(departmentMapper.toResponse(department)).thenReturn(response);

        DepartmentResponseDTO result = departmentService.getDepartmentByNumber(101);

        assertEquals(101, result.number());
    }

    @Test
    @DisplayName("Should throw when department by number not found")
    void shouldThrowWhenDepartmentByNumberNotFound() {
        when(departmentRepository.findByNumber(999)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentByNumber(999));
    }
}
