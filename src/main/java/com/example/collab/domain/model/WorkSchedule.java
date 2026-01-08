package com.example.collab.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WorkSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  private String description;

  @Getter
  @Setter
  private Integer workDaysPerWeek;

  @Getter
  @Setter
  private Integer restDaysPerWeek;

}

