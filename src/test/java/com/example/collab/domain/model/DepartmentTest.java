package com.example.collab.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Department Entity Tests")
class DepartmentTest {

    @Test
    @DisplayName("Should create department with fields")
    void shouldCreateDepartmentWithFields() {
        Department department = new Department();
        department.setName("Engineering");
        department.setNumber(101);
        department.setInitialDate(LocalDate.of(2024, 1, 1));
        department.setEndDate(LocalDate.of(2025, 12, 31));
        department.setManagerRegistration(1001);
        department.setManagerSupportRegistration(List.of(1002, 1003));

        assertEquals("Engineering", department.getName());
        assertEquals(101, department.getNumber());
        assertEquals(2, department.getManagerSupportRegistration().size());
    }
}
