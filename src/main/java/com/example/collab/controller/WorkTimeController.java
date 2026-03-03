package com.example.collab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;
import com.example.collab.service.WorkTimeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@RestController
@Validated
@RequestMapping("/work-time")
public class WorkTimeController {

    private WorkTimeService workTimeService;

    @Autowired
    public WorkTimeController(WorkTimeService workTimeService) {

        this.workTimeService = workTimeService;

    }

    @PostMapping
    public ResponseEntity<WorkTimeResponseDTO> createWorkTime(@RequestBody @Valid WorkTimeRequestDTO body) {

        WorkTimeResponseDTO response = workTimeService.createWorkTime(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<WorkTimeResponseDTO>> getAllWorkTimes() {

        List<WorkTimeResponseDTO> response = workTimeService.getAllWorkTimes();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkTimeResponseDTO> getWorkTimeById(
        @PathVariable
        @NotNull(message = "Work time ID must not be null")
        @Positive(message = "Work time ID must be a positive integer")
        Long id) {

            WorkTimeResponseDTO response = workTimeService.getWorkTimeById(id);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/active")
    public ResponseEntity<List<WorkTimeResponseDTO>> getActiveWorkTimes() {

        List<WorkTimeResponseDTO> response = workTimeService.getActiveWorkTimes();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkTimeResponseDTO> updateWorkTime(
        @PathVariable
        @NotNull(message = "Work time ID must not be null")
        @Positive(message = "Work time ID must be a positive integer")
        Long id,
        @RequestBody @Valid WorkTimeRequestDTO body) {

        WorkTimeResponseDTO response = workTimeService.updateWorkTime(id, body);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<WorkTimeResponseDTO> activateWorkTime(
        @PathVariable
        @NotNull(message = "Work time ID must not be null")
        @Positive(message = "Work time ID must be a positive integer")
        Long id) {

        WorkTimeResponseDTO response = workTimeService.activateWorkTime(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<WorkTimeResponseDTO> deactivateWorkTime(
        @PathVariable
        @NotNull(message = "Work time ID must not be null")
        @Positive(message = "Work time ID must be a positive integer")
        Long id) {

        WorkTimeResponseDTO response = workTimeService.deactivateWorkTime(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    
}
