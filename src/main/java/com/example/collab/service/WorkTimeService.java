package com.example.collab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.mapper.WorkTimeMapper;
import com.example.collab.repository.WorkTimeRepository;
import com.example.collab.service.validation.WorkTimeValidator;

@Service
public class WorkTimeService {

  private final WorkTimeRepository workTimeRepository;

  private final WorkTimeValidator workTimeValidator;

  private final WorkTimeMapper workTimeMapper;
  
  @Autowired
  public WorkTimeService(WorkTimeRepository workTimeRepository, WorkTimeValidator workTimeValidator, WorkTimeMapper workTimeMapper) {

    this.workTimeRepository = workTimeRepository;

    this.workTimeValidator = workTimeValidator;

    this.workTimeMapper = workTimeMapper;
    
  }

}
