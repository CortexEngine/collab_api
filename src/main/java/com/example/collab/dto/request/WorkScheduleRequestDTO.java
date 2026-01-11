package com.example.collab.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record WorkScheduleRequestDTO(

  @NotBlank
  @NotNull
  String description,

  @NotNull
  @NotBlank
  @Min(1)
  @Max(7)
  Integer workDaysPerWeek,

  @NotNull
  @NotBlank
  @Min(1)
  @Max(7)
  Integer restDaysPerWeek,

  @NotNull
  @NotBlank
  boolean isActive

) {};