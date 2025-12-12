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
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByNumber(
        @PathVariable
        @NotNull(message = "Department number must not be null")
        @Positive(message = "Department number must be a positive integer")
        @NotBlank(message = "Department number must not be blank")
        Integer number) {
        
            DepartmentResponseDTO response = departmentService.getDepartmentByNumber(number);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByName(
        @PathVariable
        @NotNull(message = "Department name must not be null")
        @NotBlank(message = "Department name must not be blank")
        @Size(min = 1, message = "Department name must have at least 1 characters")
        @Size(max = 64, message = "Department name must have at most 64 characters")
        String name) {
        
            DepartmentResponseDTO response = departmentService.getDepartmentByName(name);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        
    }

    @GetMapping("/manager/{registration}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentsByManager(
        @PathVariable
        @NotNull(message = "Manager registration must not be null")
        @Positive(message = "Manager registration must be a positive integer")
        @NotBlank(message = "Manager registration must not be blank")
        Integer registration) {

            DepartmentResponseDTO response = departmentService.getDepartmentByManagerRegistration(registration);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        }

    @GetMapping("/support_manager/{registration}")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentsBySupportManager(
        @PathVariable
        @NotNull(message = "Support manager registration must not be null")
        @Positive(message = "Support manager registration must be a positive integer")
        @NotBlank(message = "Support manager registration must not be blank")
        Integer registration) {

            List<DepartmentResponseDTO> response = departmentService.getDepartmentsBySupportManagerRegistration(registration);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        }
    
    @GetMapping("/team_member")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentsByTeamMember(
        @ResquestParam
        @NotNull(message = "Team member registration must not be null")
        @Positive(message = "Team member registration must be a positive integer")
        @NotBlank(message = "Team member registration must not be blank")
        @Size(min = 1, message = "At least one team member registration must be provided")
        List<Integer> registrations) {

            List<DepartmentResponseDTO> response = departmentService.getDepartmentsByTeamMemberRegistration(registrations);

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
