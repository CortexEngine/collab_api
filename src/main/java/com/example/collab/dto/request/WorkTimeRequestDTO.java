package com.example.collab.dto.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record WorkTimeRequestDTO(

  @NotBlank
  @NotNull
  String description,

  @NotNull
  Boolean isActive,

  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialTime,

  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endTime,

  @NotNull
  Boolean isOvernight,

  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialBreakTime,

  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endBreakTime,

  @NotNull
  Boolean requiresPunch,

  @NotNull
  Boolean autoGeneratePunches

) {};
