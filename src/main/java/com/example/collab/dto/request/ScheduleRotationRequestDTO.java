package com.example.collab.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ScheduleRotationRequestDTO {

  @NotBlank
  @NotNull
  @PositiveOrZero
  private Integer workSchedule;

  @NotBlank
  @NotNull
  @PositiveOrZero
  private Integer workTime;

  @NotBlank
  @NotNull
  @Min(1)
  @Max(7)
  private Integer dayOfWeek;

}
