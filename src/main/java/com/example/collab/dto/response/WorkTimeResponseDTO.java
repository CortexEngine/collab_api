package com.example.collab.dto.response;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record WorkTimeResponseDTO (

  String description,

  Boolean isActive,

  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialTime,

  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endTime,

  Boolean isOvernight,

  @JsonFormat(pattern="HH:mm:ss")
  LocalTime initialBreakTime,

  @JsonFormat(pattern="HH:mm:ss")
  LocalTime endBreakTime,

  Boolean requiresPunch,

  Boolean autoGeneratePunches

) {};
