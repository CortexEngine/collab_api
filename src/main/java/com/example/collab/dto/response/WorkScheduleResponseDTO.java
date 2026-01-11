package com.example.collab.dto.response;

import lombok.Data;

@Data
public class WorkScheduleResponseDTO {

  private String description;

  private Integer workDaysPerWeek;

  private Integer restDaysPerWeek;

  private boolean isActive;

}
