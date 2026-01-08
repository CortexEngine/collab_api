package com.example.collab.domain.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WorkTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  private String description;

  @Getter
  @Setter
  private boolean isActive;

  @Getter
  private LocalTime initialTime;

  @Getter
  private LocalTime endTime;

  @Getter
  @Setter
  private LocalTime initialBreakTime;

  @Getter
  @Setter
  private LocalTime endBreakTime;

  @Getter
  @Setter
  private boolean requiresPunch;

  @Getter
  @Setter
  private boolean autoGeneratePunches;

}

