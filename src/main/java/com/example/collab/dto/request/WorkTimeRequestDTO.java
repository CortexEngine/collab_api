package com.example.collab.dto.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkTimeRequestDTO {

  @NotBlank
  @NotNull
  private String description;

  @NotNull
  @NotBlank
  private boolean isActive;

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime initialTime;

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime endTime;

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime initialBreakTime;

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime endBreakTime;

  @NotBlank
  @NotNull
  private boolean requiresPunch;

  @NotBlank
  @NotNull
  private boolean autoGeneratePunches;


}
