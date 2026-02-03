package com.example.collab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.collab.dto.request.ScheduleRotationRequestDTO;
import com.example.collab.dto.response.ScheduleRotationResponseDTO;
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

  public ScheduleRotationResponseDTO createScheduleRotation(ScheduleRotationRequestDTO req) {

    scheduleRotationValidator.validateDayIndexesAndWorkdaysSize(req.dayIndexs(), req.workdays());

    scheduleRotationValidator.validateMaxDayIndexPerSchedule(req.workSchedule(), req.workdays());

    scheduleRotationValidator.validateMinDayIndexPerSchedule(req.workSchedule(), req.workdays());

    scheduleRotationValidator.validateTotalDaysMatchSchedule(req.workSchedule(), req.dayIndexs());

    scheduleRotationValidator.validateDuplicateDayIndex(req.workSchedule(), req.dayIndexs());

    var scheduleRotation = scheduleRotationMapper.toEntity(req);

    var savedScheduleRotation = scheduleRotationRepository.save(scheduleRotation);

    return scheduleRotationMapper.toResponse(savedScheduleRotation);

  }

  public ScheduleRotationResponseDTO getScheduleRotationByWorkScheduleId(Long id) {

    var scheduleRotation = scheduleRotationRepository.findByWorkScheduleId(id).orElseThrow(() -> new IllegalArgumentException("Schedule rotation not found"));

    return scheduleRotationMapper.toResponse(scheduleRotation);

  }

}
