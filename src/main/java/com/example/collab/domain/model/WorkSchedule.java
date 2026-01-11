package com.example.collab.domain.model;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Audited
@Data
public class WorkSchedule {

  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private Integer workDaysPerWeek;

  private Integer restDaysPerWeek;

  private boolean isActive;

}

