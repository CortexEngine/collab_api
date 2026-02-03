package com.example.collab.dto.response;

import java.util.List;

public record ScheduleRotationResponseDTO (

  Integer workSchedule,

  Integer workTime,

  List<Integer> dayIndexs,

  List<Boolean> workdays

) {};
