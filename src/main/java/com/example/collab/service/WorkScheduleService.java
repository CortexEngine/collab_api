package com.example.collab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.mapper.WorkScheduleMapper;
import com.example.collab.repository.WorkScheduleRepository;
import com.example.collab.service.validation.WorkScheduleValidator;

@Service
public class WorkScheduleService {

  private WorkScheduleRepository workScheduleRepository;

  private WorkScheduleValidator workScheduleValidator;

  private WorkScheduleMapper workScheduleMapper;

  @Autowired
  public WorkScheduleService(WorkScheduleRepository workScheduleRepository, WorkScheduleValidator workScheduleValidator, WorkScheduleMapper workScheduleMapper) {

    this.workScheduleRepository = workScheduleRepository;

    this.workScheduleValidator = workScheduleValidator;

    this.workScheduleMapper = workScheduleMapper;
  }

}
