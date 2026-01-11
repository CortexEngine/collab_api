package com.example.collab.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ScheduleRotation {

  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private WorkSchedule workSchedule;

  @ManyToOne
  @JoinColumn(name = "work_time_id")
  private WorkTime workTime;

  private Integer dayIndex;

  private Boolean workday;

}

