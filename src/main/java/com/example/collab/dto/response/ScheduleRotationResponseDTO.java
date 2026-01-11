package com.example.collab.dto.response;

public record ScheduleRotationResponseDTO (

  Integer workSchedule,

  Integer workTime,

  Integer dayIndex,

  Boolean workday

) {};
