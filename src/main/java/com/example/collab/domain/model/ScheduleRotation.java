package com.example.collab.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRotation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private WorkSchedule workSchedule;

  @ManyToOne
  @JoinColumn(name = "work_time_id")
  private WorkTime workTime;

  @Getter
  @Setter
  private Integer dayOfWeek;

}

