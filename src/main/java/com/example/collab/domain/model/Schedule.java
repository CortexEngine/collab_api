package com.example.collab.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;

@Entity
public class Schedule {

  private Long id;
  private Long scheduleCode;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalTime initialTime;
  private LocalTime endTime;
  private LocalTime initialBreakTime;
  private LocalTime endBreakTime;
  private boolean timeInitialEndGenerated;
  private boolean timeBreakGenerated;
  private boolean hasNightHours;
  private Double nightHoursCount;
  private Double dailyHoursCount;

}

