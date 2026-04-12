package com.example.collab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.collab.repository.DepartmentRepository;
import com.example.collab.service.port.DomainEventPublisher;
import com.example.collab.service.validation.DepartmentValidator;
import com.example.collab.mapper.DepartmentMapper;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.exception.business.BadRequestException;
import com.example.collab.exception.resource.DepartmentNotFoundException;
import com.example.collab.domain.model.Department;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentValidator departmentValidator;

    private final DepartmentMapper departmentMapper;

    private final DomainEventPublisher eventPublisher;
    
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentValidator departmentValidator, DepartmentMapper departmentMapper, DomainEventPublisher eventPublisher){

        this.departmentRepository = departmentRepository;

        this.departmentValidator = departmentValidator;

        this.departmentMapper = departmentMapper;

        this.eventPublisher = eventPublisher;

    }

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO req){

        departmentValidator.validateDepartmentName(req.name());

        departmentValidator.validateDepartmentNumber(req.number());

        departmentValidator.validateDepartmentManager(req.managerRegistration());

        for(Integer registration : req.managerSupportRegistration()){

           departmentValidator.validateDepartmentSupportManager(registration);

        }

        for(Integer registration : req.teamMembersRegistration()){

            departmentValidator.validateDepartmentMembers(registration);

        }

        Department department = departmentMapper.toEntity(req);

        if(department != null){
            
            Department savedDepartment = departmentRepository.save(department);

            DepartmentResponseDTO response = departmentMapper.toResponse(savedDepartment);

            eventPublisher.publish("DEPARTMENT", savedDepartment.getId().toString(), "DEPARTMENT_CREATED", response);

            return response;
        }

        throw new BadRequestException("Error creating department");
    }

    public List<DepartmentResponseDTO> getAllDepartments(){
        
        List<Department> departments = departmentRepository.findAll();

        if(departments.isEmpty()){
             
            throw new DepartmentNotFoundException("Department not found");
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public DepartmentResponseDTO getDepartmentByNumber(Integer number){

        Department department = departmentRepository.findByNumber(number)
        .orElseThrow(() -> new DepartmentNotFoundException("Department number not found"));

        return departmentMapper.toResponse(department);

    }

    public DepartmentResponseDTO getDepartmentByName(String name){

        Department department = departmentRepository.findByName(name)
        .orElseThrow(() -> new DepartmentNotFoundException("Department name not found"));

        return departmentMapper.toResponse(department);

    }

    public List<DepartmentResponseDTO> getDepartmentsByManagerRegistration(Integer managerRegistration){

        List<Department> departments = departmentRepository.findByManagerRegistration(managerRegistration);
        
        if(departments.isEmpty()){

            throw new DepartmentNotFoundException("Department not found for manager registration: " + managerRegistration);
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public List<DepartmentResponseDTO> getDepartmentsBySupportManagerRegistration(Integer managerSupportRegistration){

        List<Department> departments = departmentRepository.findByManagerSupportRegistrationContains(managerSupportRegistration);

        if(departments.isEmpty()){

            throw new DepartmentNotFoundException("Department not found for support manager registration: " + managerSupportRegistration);
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public List<DepartmentResponseDTO> getDepartmentsByTeamMemberRegistration(List<Integer> teamMemberRegistration){

        List<Department> departments = departmentRepository.findByTeamMembersRegistrationIn(teamMemberRegistration);

        if(departments.isEmpty()){

            throw new DepartmentNotFoundException("Department not found for team member registration: " + teamMemberRegistration);
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    @Transactional
    public Integer deleteDepartmentByNumber(Integer number){

        Department department =  departmentRepository.findByNumber(number)
        .orElseThrow(() -> new DepartmentNotFoundException("Department number not found"));

        String aggregateId = department.getId().toString();
        DepartmentResponseDTO snapshot = departmentMapper.toResponse(department);

        departmentRepository.delete(department);

        eventPublisher.publish("DEPARTMENT", aggregateId, "DEPARTMENT_DELETED", snapshot);

        return number;
    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(Integer number, DepartmentRequestDTO req){

        Department existingDepartment = departmentRepository.findByNumber(number).orElseThrow(
                () -> new DepartmentNotFoundException("Department not found with number: " + number));
        
        departmentValidator.validateDepartmentNumber(req.number());

        departmentValidator.validateDepartmentName(req.name());

        departmentValidator.validateDepartmentManager(req.managerRegistration());

        departmentValidator.validateDepartmentSupportManager(req.managerSupportRegistration());

        departmentValidator.validateDepartmentMembers(req.teamMembersRegistration());

        departmentMapper.updateEntity(existingDepartment, req);

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        DepartmentResponseDTO response = departmentMapper.toResponse(updatedDepartment);

        eventPublisher.publish("DEPARTMENT", updatedDepartment.getId().toString(), "DEPARTMENT_UPDATED", response);

        return response;

    }

}
