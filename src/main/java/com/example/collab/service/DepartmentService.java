package com.example.collab.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.collab.repository.DepartmentRepository;
import com.example.collab.service.validation.DepartmentValidator;
import com.example.collab.mapper.DepartmentMapper;
import com.example.collab.dto.request.DepartmentRequestDTO;
import com.example.collab.dto.response.DepartmentResponseDTO;
import com.example.collab.exception.business.BadRequestException;
import com.example.collab.domain.model.Department;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    private DepartmentValidator departmentValidator;

    private DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentValidator departmentValidator, DepartmentMapper departmentMapper){

        this.departmentRepository = departmentRepository;

        this.departmentValidator = departmentValidator;

        this.departmentMapper = departmentMapper;

    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO req){

        departmentValidator.validateDepartmentName(req.getName());

        departmentValidator.validateDepartmentNumber(req.getNumber());

        departmentValidator.validateDepartmentManager(req.getManagerRegistration());

        for(Integer registration : req.getManagerSupportRegistration()){

           departmentValidator.validateDepartmentSupportManager(registration);

        }

        for(Integer registration : req.getTeamMembersRegistration()){

            departmentValidator.validateDepartmentMembers(registration);

        }

        Department department = departmentMapper.toEntity(req);

        if(department != null){
            
            Department savedDepartment = departmentRepository.save(department);

            return departmentMapper.toResponse(savedDepartment); 
        }

        throw new BadRequestException("Error creating department");
    }

    public List<DepartmentResponseDTO> getAllDepartments(){
        
        List<Department> departments = departmentRepository.findAll();

        if(departments.isEmpty()){
             
            throw new RuntimeException("Department not found");
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public DepartmentResponseDTO getDepartmentByNumber(Integer number){

        Department department = departmentRepository.findByNumber(number)
        .orElseThrow(() -> new RuntimeException("Department number not found"));

        return departmentMapper.toResponse(department);

    }

    public DepartmentResponseDTO getDepartmentByName(String name){

        Department department = departmentRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException("Department name not found"));

        return departmentMapper.toResponse(department);

    }

    public DepartmentResponseDTO getDepartmentByManagerRegistration(Integer managerRegistration){

        Department department = departmentRepository.findByManagerRegistration(managerRegistration)
        .orElseThrow(() -> new RuntimeException("Department manager registration not found"));
        
        return departmentMapper.toResponse(department);

    }

    public List<DepartmentResponseDTO> getDepartmentsBySupportManagerRegistration(Integer managerSupportRegistration){

        List<Department> departments = departmentRepository.findByManagerSupportRegistrationContains(managerSupportRegistration);

        if(departments.isEmpty()){
             
            throw new RuntimeException("Department not found for support manager registration: " + managerSupportRegistration);
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public List<DepartmentResponseDTO> getDepartmentsByTeamMemberRegistration(Integer teamMemberRegistration){

        List<Department> departments = departmentRepository.findByTeamMembersRegistrationContains(teamMemberRegistration);

        if(departments.isEmpty()){
             
            throw new RuntimeException("Department not found for team member registration: " + teamMemberRegistration);
        }

        return departments.stream().map(department -> departmentMapper.toResponse(department)).toList();

    }

    public Integer deleteDepartmentByNumber(Integer number){

        Department department =  departmentRepository.findByNumber(number)
        .orElseThrow(() -> new RuntimeException("Department number not found"));

        departmentRepository.delete(department);

        return number;
    }

    public DepartmentResponseDTO updateDepartment(Integer number, DepartmentRequestDTO req){

        Department existingDepartment = departmentRepository.findByNumber(number).orElseThrow(
                () -> new BadRequestException("Department not found with number: " + number));
        
        departmentValidator.validateDepartmentNumber(req.getNumber());

        departmentValidator.validateDepartmentName(req.getName());

        departmentValidator.validateDepartmentManager(req.getManagerRegistration());

        departmentValidator.validateDepartmentSupportManager(req.getManagerSupportRegistration());

        departmentValidator.validateDepartmentMembers(req.getTeamMembersRegistration());

        departmentMapper.updateEntity(existingDepartment, req);

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        return departmentMapper.toResponse(updatedDepartment);

    }

}
