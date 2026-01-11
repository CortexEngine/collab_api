package com.example.collab.domain.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class WorkTime {

  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private boolean isActive;

  @Setter(AccessLevel.NONE)
  private LocalTime initialTime;

  @Setter(AccessLevel.NONE)
  private LocalTime endTime;

  private LocalTime initialBreakTime;

  private LocalTime endBreakTime;

  private boolean requiresPunch;

  private boolean autoGeneratePunches;

  public WorkTime(Long id) {

    this.id = id;
    
  }

}

