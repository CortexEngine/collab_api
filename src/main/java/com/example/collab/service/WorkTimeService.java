package com.example.collab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.domain.model.WorkTime;
import com.example.collab.dto.request.WorkTimeRequestDTO;
import com.example.collab.dto.response.WorkTimeResponseDTO;
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

  public WorkTimeResponseDTO createWorkTime(WorkTimeRequestDTO req) {

    workTimeValidator.validateTimeEndBeforeInitialTime(req.initialTime(), req.endTime(), req.isOvernight());

    workTimeValidator.validateSameWorkTimes(req.initialTime(), req.endTime(), req.initialBreakTime(), req.endBreakTime());

    workTimeValidator.validateInitialAndEndWorkTimeLimit(req.initialTime(), req.endTime(), req.isOvernight());

    workTimeValidator.validateInitialAndEndBreakTimeLimit(req.initialBreakTime(), req.endBreakTime(), req.isOvernight());

    workTimeValidator.validateRequireAndAutoPunchSameTime(req.requiresPunch(), req.autoGeneratePunches());

    WorkTime workTime = workTimeMapper.toEntity(req);

    WorkTime savedWorkTime = workTimeRepository.save(workTime);

    return workTimeMapper.toResponse(savedWorkTime);

  }

  public WorkTimeResponseDTO getWorkTimeById(Long id) {

    WorkTime workTime = workTimeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Work time with id " + id + " not found."));

    return workTimeMapper.toResponse(workTime);

  }

}
