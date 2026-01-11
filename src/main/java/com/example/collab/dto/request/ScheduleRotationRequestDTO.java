package com.example.collab.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ScheduleRotationRequestDTO(

  @NotNull
  @PositiveOrZero
  Integer workSchedule,

  @NotNull
  @PositiveOrZero
  Integer workTime,

  @NotNull
  @Min(1)
  @Max(7)
  Integer dayOfWeek

) {};
