package com.example.collab.domain.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ScheduleRotation {

  public ScheduleRotation() {

  }

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

  @ElementCollection
  @CollectionTable(name = "schedule_rotation_day_indexes", joinColumns = @JoinColumn(name = "schedule_rotation_id"))
  @Column(name = "day_index")
  private List<Integer> dayIndexs;

  @ElementCollection
  @CollectionTable(name = "schedule_rotation_workdays", joinColumns = @JoinColumn(name = "schedule_rotation_id"))
  @Column(name = "workday")
  private List<Boolean> workdays;

}

