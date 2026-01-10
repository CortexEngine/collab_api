package com.example.collab.dto.request;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkTimeRequestDTO {

  @NotBlank
  @NotNull
  private String description;

  @NotNull
  @NotBlank
  private boolean isActive;

  @NotBlank
  @NotNull
  private LocalTime initialTime;

  @NotBlank
  @NotNull
  private LocalTime endTime;

  @NotBlank
  @NotNull
  private LocalTime initialBreakTime;

  @NotBlank
  @NotNull
  private LocalTime endBreakTime;

  @NotBlank
  @NotNull
  private boolean requiresPunch;

  @NotBlank
  @NotNull
  private boolean autoGeneratePunches;


}
