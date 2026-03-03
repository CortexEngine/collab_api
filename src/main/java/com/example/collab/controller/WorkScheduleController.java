package com.example.collab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;
import com.example.collab.service.WorkScheduleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@RestController
@Validated
@RequestMapping("/work-schedules")
public class WorkScheduleController {

    private WorkScheduleService workScheduleService;

    @Autowired
    public WorkScheduleController(WorkScheduleService workScheduleService) {

        this.workScheduleService = workScheduleService;

    }

    @PostMapping
    public ResponseEntity<WorkScheduleResponseDTO> createWorkSchedule(@RequestBody @Valid WorkScheduleRequestDTO body) {

        WorkScheduleResponseDTO response = workScheduleService.createWorkSchedule(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<WorkScheduleResponseDTO>> getAllWorkSchedule() {

        List<WorkScheduleResponseDTO> response = workScheduleService.getAllWorkSchedule();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleResponseDTO> getActiveWorkScheduleById(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long id) {

            WorkScheduleResponseDTO response = workScheduleService.getActiveWorkScheduleById(id);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/description/{description}")
    public ResponseEntity<WorkScheduleResponseDTO> getWorkScheduleByDescription(
        @PathVariable
        @NotNull(message = "Work schedule description must not be null")
        @NotBlank(message = "Work schedule description must not be blank")
        @Size(min = 1, message = "Work schedule description must have at least 1 character")
        @Size(max = 255, message = "Work schedule description must have at most 255 characters")
        String description) {

            WorkScheduleResponseDTO response = workScheduleService.getWorkScheduleByDescription(description);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/active")
    public ResponseEntity<List<WorkScheduleResponseDTO>> getAllActiveWorkSchedule() {

        List<WorkScheduleResponseDTO> response = workScheduleService.getAllActiveWorkSchedule();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/deactivated")
    public ResponseEntity<List<WorkScheduleResponseDTO>> getAllDeactivatedWorkSchedule() {

        List<WorkScheduleResponseDTO> response = workScheduleService.getAllDeactivatedWorkSchedule();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkScheduleResponseDTO> updateWorkSchedule(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long id,
        @RequestBody @Valid WorkScheduleRequestDTO body) {

        WorkScheduleResponseDTO response = workScheduleService.updateWorkSchedule(id, body);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<WorkScheduleResponseDTO> activateWorkSchedule(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long id) {

        WorkScheduleResponseDTO response = workScheduleService.activateWorkSchedule(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<WorkScheduleResponseDTO> deactivateWorkSchedule(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long id) {

        WorkScheduleResponseDTO response = workScheduleService.deactivateWorkSchedule(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkSchedule(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long id) {

        workScheduleService.deleteWorkSchedule(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
