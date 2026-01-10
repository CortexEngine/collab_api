package com.example.collab.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkScheduleRequestDTO {

  @NotBlank
  @NotNull
  private String description;

  @NotNull
  @NotBlank
  @Min(1)
  @Max(7)
  private Integer workDaysPerWeek;

  @NotNull
  @NotBlank
  @Min(1)
  @Max(7)
  private Integer restDaysPerWeek;

  @NotNull
  @NotBlank
  private boolean isActive;

}
