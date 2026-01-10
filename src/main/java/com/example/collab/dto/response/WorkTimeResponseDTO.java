package com.example.collab.dto.response;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class WorkTimeResponseDTO {

  private String description;

  private boolean isActive;

  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime initialTime;

  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime endTime;

  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime initialBreakTime;

  @JsonFormat(pattern="HH:mm:ss")
  private LocalTime endBreakTime;

  private boolean requiresPunch;

  private boolean autoGeneratePunches;

}
