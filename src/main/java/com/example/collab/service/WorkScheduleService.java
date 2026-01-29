package com.example.collab.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.domain.model.WorkSchedule;
import com.example.collab.dto.request.WorkScheduleRequestDTO;
import com.example.collab.dto.response.WorkScheduleResponseDTO;
import com.example.collab.mapper.WorkScheduleMapper;
import com.example.collab.repository.WorkScheduleRepository;
import com.example.collab.service.validation.WorkScheduleValidator;

@Service
public class WorkScheduleService {

  private final WorkScheduleRepository workScheduleRepository;

  private final WorkScheduleValidator workScheduleValidator;

  private final WorkScheduleMapper workScheduleMapper;
  
  @Autowired
  public WorkScheduleService(WorkScheduleRepository workScheduleRepository, WorkScheduleValidator workScheduleValidator, WorkScheduleMapper workScheduleMapper) {

    this.workScheduleRepository = workScheduleRepository;

    this.workScheduleValidator = workScheduleValidator;

    this.workScheduleMapper = workScheduleMapper;
  }

  public WorkScheduleResponseDTO createWorkSchedule(WorkScheduleRequestDTO req) {

    workScheduleValidator.validateManyWorkDaysPerWeek(req.workDaysPerWeek());

    workScheduleValidator.validateManyRestDaysPerWeek(req.restDaysPerWeek());

    workScheduleValidator.validateManyDaysPerWeek(req.workDaysPerWeek(), req.restDaysPerWeek());

    workScheduleValidator.validateDescriptionExists(req.description());

    WorkSchedule workSchedule = workScheduleMapper.toEntity(req);

    WorkSchedule savedWorkSchedule = workScheduleRepository.save(workSchedule);

    return workScheduleMapper.toResponse(savedWorkSchedule);

  }

  public WorkScheduleResponseDTO getActiveWorkScheduleById(Long id) {

    workScheduleValidator.validateIsActiveWorkSchedule(id);

    WorkSchedule workSchedule = workScheduleRepository.findByIdAndIsActive(id, true).get();

    return workScheduleMapper.toResponse(workSchedule);

  }

  public List<WorkScheduleResponseDTO> getAllWorkSchedule(){

    List<WorkSchedule> workSchedules = workScheduleRepository.findAll();

    return workSchedules.stream().map(workSchedule -> workScheduleMapper.toResponse(workSchedule)).collect(Collectors.toList());

  }

  public WorkScheduleResponseDTO getWorkScheduleByDescription(String description) {

    workScheduleValidator.validateDescriptionExists(description);

    WorkSchedule workSchedule = workScheduleRepository.findByDescription(description).get();

    return workScheduleMapper.toResponse(workSchedule);

  }

  public List<WorkScheduleResponseDTO> getAllActiveWorkSchedule(){

    List<WorkSchedule> activeWorkSchedules = workScheduleRepository.findByIsActive(true);

    return activeWorkSchedules.stream().map(workSchedule -> workScheduleMapper.toResponse(workSchedule)).collect(Collectors.toList());

  }

}