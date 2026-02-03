package com.example.collab.domain.model;

import java.util.List;

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

  private List<Integer> dayIndexs;

  private List<Boolean> workdays;

}

