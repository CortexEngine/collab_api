package com.example.collab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.mapper.ScheduleRotationMapper;
import com.example.collab.repository.ScheduleRotationRepository;
import com.example.collab.service.validation.ScheduleRotationValidator;

@Service
public class ScheduleRotationService {

  private final ScheduleRotationRepository scheduleRotationRepository;

  private final ScheduleRotationValidator scheduleRotationValidator;

  private final ScheduleRotationMapper scheduleRotationMapper;

  @Autowired
  public ScheduleRotationService(ScheduleRotationRepository scheduleRotationRepository, ScheduleRotationValidator scheduleRotationValidator, ScheduleRotationMapper scheduleRotationMapper) {

    this.scheduleRotationRepository = scheduleRotationRepository;

    this.scheduleRotationValidator = scheduleRotationValidator;

    this.scheduleRotationMapper = scheduleRotationMapper;

  }

}
