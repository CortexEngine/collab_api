package com.example.collab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;
import com.example.collab.service.ScheduleRotationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@RestController
@Validated
@RequestMapping("/schedule-rotations")
public class ScheduleRotationController {

    private ScheduleRotationService scheduleRotationService;

    @Autowired
    public ScheduleRotationController(ScheduleRotationService scheduleRotationService) {

        this.scheduleRotationService = scheduleRotationService;

    }

    @PostMapping
    public ResponseEntity<ScheduleRotationResponseDTO> createScheduleRotation(@RequestBody @Valid ScheduleRotationRequestDTO body) {

        ScheduleRotationResponseDTO response = scheduleRotationService.createScheduleRotation(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/work-schedule/{workScheduleId}")
    public ResponseEntity<ScheduleRotationResponseDTO> getScheduleRotationByWorkScheduleId(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long workScheduleId) {

            ScheduleRotationResponseDTO response = scheduleRotationService.getScheduleRotationByWorkScheduleId(workScheduleId);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/work-schedule/{workScheduleId}/work-time/{workTimeId}")
    public ResponseEntity<ScheduleRotationResponseDTO> getScheduleRotationByWorkScheduleIdAndWorkTimeId(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long workScheduleId,
        @PathVariable
        @NotNull(message = "Work time ID must not be null")
        @Positive(message = "Work time ID must be a positive integer")
        Long workTimeId) {

            ScheduleRotationResponseDTO response = scheduleRotationService.getScheduleRotationByWorkScheduleIdAndWorkTimeId(workScheduleId, workTimeId);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/work-schedule/{workScheduleId}/day-index/{dayIndexs}")
    public ResponseEntity<ScheduleRotationResponseDTO> getScheduleRotationByWorkScheduleAndDayIndexs(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long workScheduleId,
        @PathVariable
        @NotNull(message = "Day index must not be null")
        @Positive(message = "Day index must be a positive integer")
        Integer dayIndexs) {

            ScheduleRotationResponseDTO response = scheduleRotationService.getScheduleRotationByWorkScheduleAndDayIndexs(workScheduleId, dayIndexs);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleRotationResponseDTO> updateScheduleRotation(
        @PathVariable
        @NotNull(message = "Schedule rotation ID must not be null")
        @Positive(message = "Schedule rotation ID must be a positive integer")
        Long id,
        @RequestBody @Valid ScheduleRotationRequestDTO body) {

        ScheduleRotationResponseDTO response = scheduleRotationService.updateScheduleRotation(id, body);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/work-schedule/{workScheduleId}")
    public ResponseEntity<Void> deleteScheduleRotation(
        @PathVariable
        @NotNull(message = "Work schedule ID must not be null")
        @Positive(message = "Work schedule ID must be a positive integer")
        Long workScheduleId) {

        scheduleRotationService.deleteScheduleRotation(workScheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
