package com.example.collab.service;

import java.util.List;
import java.util.stream.Collectors;

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

  public List<WorkTimeResponseDTO> getAllWorkTimes() {

    List<WorkTime> workTimes = workTimeRepository.findAll();

    return workTimes.stream().map(workTime -> workTimeMapper.toResponse(workTime)).collect(Collectors.toList());

  }

  public List<WorkTimeResponseDTO> getActiveWorkTimes() {

    List<WorkTime> workTimes = workTimeRepository.findByIsActive(true);

    return workTimes.stream().map(workTime -> workTimeMapper.toResponse(workTime)).collect(Collectors.toList());
  
  }

  public WorkTimeResponseDTO updateWorkTime(Long id, WorkTimeRequestDTO req) {

    WorkTime existingWorkTime = workTimeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Work time with id " + id + " not found."));

    workTimeValidator.validateIsActiveWorkTime(id);

    workTimeValidator.validateTimeEndBeforeInitialTime(req.initialTime(), req.endTime(), req.isOvernight());

    workTimeValidator.validateSameWorkTimes(req.initialTime(), req.endTime(), req.initialBreakTime(), req.endBreakTime());

    workTimeValidator.validateInitialAndEndWorkTimeLimit(req.initialTime(), req.endTime(), req.isOvernight());

    workTimeValidator.validateInitialAndEndBreakTimeLimit(req.initialBreakTime(), req.endBreakTime(), req.isOvernight());

    workTimeValidator.validateRequireAndAutoPunchSameTime(req.requiresPunch(), req.autoGeneratePunches());

    existingWorkTime.setDescription(req.description());

    existingWorkTime.setInitialTime(req.initialTime());

    existingWorkTime.setEndTime(req.endTime());
    
    existingWorkTime.setInitialBreakTime(req.initialBreakTime());
    
    existingWorkTime.setEndBreakTime(req.endBreakTime());
    
    existingWorkTime.setIsOvernight(req.isOvernight());
    
    existingWorkTime.setRequiresPunch(req.requiresPunch());
    
    existingWorkTime.setAutoGeneratePunches(req.autoGeneratePunches());
    
    existingWorkTime.setIsActive(req.isActive());

    WorkTime updatedWorkTime = workTimeRepository.save(existingWorkTime);

    return workTimeMapper.toResponse(updatedWorkTime);
    
  }

  public WorkTimeResponseDTO activateWorkTime(Long id) {

    workTimeValidator.validateIsActiveWorkTime(id);

    WorkTime workTime = workTimeRepository.findByIdAndIsActive(id, false).get();

    workTime.setIsActive(true);

    WorkTime updatedWorkTime = workTimeRepository.save(workTime);

    return workTimeMapper.toResponse(updatedWorkTime);

  }

}
