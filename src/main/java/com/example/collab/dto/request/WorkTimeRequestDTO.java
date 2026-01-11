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
  @NotBlank
  boolean isActive,

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialTime,

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endTime,

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialBreakTime,

  @NotBlank
  @NotNull
  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endBreakTime,

  @NotBlank
  @NotNull
  boolean requiresPunch,

  @NotBlank
  @NotNull
  boolean autoGeneratePunches


) {};
