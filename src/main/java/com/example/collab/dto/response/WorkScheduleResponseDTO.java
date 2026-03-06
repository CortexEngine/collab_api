package com.example.collab.dto.response;

public record WorkScheduleResponseDTO (

  String description,

  Integer workDaysPerWeek,

  Integer restDaysPerWeek,

  Boolean isActive

) {};
