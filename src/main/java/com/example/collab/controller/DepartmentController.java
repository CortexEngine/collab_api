package com.example.collab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.service.DepartmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@RestController
@Validated
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

    @GetMapping("/number/{number}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByNumber(@PathVariable Integer number) {
        
        DepartmentResponseDTO response = departmentService.getDepartmentByNumber(number);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByName(@PathVariable String name) {
        
        DepartmentResponseDTO response = departmentService.getDepartmentByName(name);

        return ResponseEntity.status(HttpStatus.OK).body(response);
        
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
