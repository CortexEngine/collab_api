package com.example.collab.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ScheduleRotationRequestDTO(
  @NotBlank
  @NotNull
  @PositiveOrZero
  Integer workSchedule,

  @NotBlank
  @NotNull
  @PositiveOrZero
  Integer workTime,

  @NotBlank
  @NotNull
  @Min(1)
  @Max(7)
  Integer dayOfWeek

) {};
