package com.example.collab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;

    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody @Valid DepartmentRequestDTO body) {

        DepartmentResponseDTO response = departmentService.createDepartment(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {

        List<DepartmentResponseDTO> response = departmentService.getAllDepartments();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartment(@PathVariable Integer id) {
        // Logic to get a department by ID

        return new DepartmentResponseDTO();
    }

    @PutMapping("/{id}")
    public void updateDepartment(@PathVariable Integer id, @RequestBody DepartmentRequestDTO body) {
        // Logic to update a department
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Integer id) {
        // Logic to delete a department
    }

}
