package com.example.collab.controller;

import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;

    }


    @PostMapping
    public void createDepartment(@RequestBody DepartmentRequestDTO body) {
        // Logic to create a department
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
