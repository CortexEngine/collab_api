package com.example.collab.dto.request;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ScheduleRotationRequestDTO(

  @NotNull
  @PositiveOrZero
  Long workSchedule,

  @NotNull
  @PositiveOrZero
  Long workTime,
  
  @NotNull
  @Size(min = 1)
  List<@NotNull @Min(1) @Max(7) Integer> dayIndexs,

  @NotNull
  @Size(min = 1)
  List<@NotNull Boolean> workdays

) {};
