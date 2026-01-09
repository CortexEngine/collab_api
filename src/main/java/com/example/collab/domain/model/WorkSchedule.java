package com.example.collab.domain.model;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class WorkSchedule {

  @Id
  @Getter
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

  @Getter
  @Setter
  private boolean isActive;

}

