package com.example.collab.service.validation; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.repository.*;
import com.example.collab.exception.business.*;
import com.example.collab.exception.domain.*;

@Component
public class DepartmentValidator {

    private DepartmentRepository departmentRepository;

    private CollaboratorRepository collaboratorRepository;

    @Autowired
    public DepartmentValidator(DepartmentRepository departmentRepository, CollaboratorRepository collaboratorRepository){

        this.departmentRepository = departmentRepository;
        
        this.collaboratorRepository = collaboratorRepository;

    }

    public void validateDepartmentManager(Integer registration){

        if(collaboratorRepository.findByRegistrationAndManager(registration, true).isEmpty()){

            throw new InvalidManagerException("This collaborator does not act as a manager.");

          }

    }

    @SuppressWarnings("unchecked")
    public void validateDepartmentSupportManager(Object registration){

        if(registration == null){

            throw new BadRequestException("Support manager cannot be null.");

        }

        if(registration instanceof Integer){
            
            validateSingleSupportManager((Integer) registration);

        } else if (registration instanceof List) {

            validateMultipleSupportManagers((List<Integer>) registration);

        }

    }

    private void validateSingleSupportManager(Integer registration){

        if(collaboratorRepository.findByRegistrationAndSupportManager(registration, true).isEmpty()){

            throw new InvalidSupportManagerException("This collaborator does not act as a support manager.");

        }

    }

    private void validateMultipleSupportManagers(List<Integer> registrations){

        for(Integer registration : registrations){

            validateSingleSupportManager(registration);
        }

    }

    public void validateDepartmentName(String name){

        if(departmentRepository.findByName(name).isPresent()){

            throw new DuplicatedNameDepartmentException("Name already exists");

        }

    }

    public void validateDepartmentNumber(Integer number){

        if(departmentRepository.findByNumber(number).isPresent()){

            throw new DuplicatedNumberDepartmentException("Number already exists");

        }
    }

    @SuppressWarnings("unchecked")
    public void validateDepartmentMembers(Object registration){

        if(registration == null){

            throw new BadRequestException("Department member cannot be null.");

        }

        if(registration instanceof Integer){

            validateSingleMember((Integer) registration);

        } else if (registration instanceof List) {

            validateMultipleMembers((List<Integer>) registration);

        } else {

            throw new BadRequestException("Invalid type for department member registration.");

        }

    }

    private void validateSingleMember(Integer registration){

        if(collaboratorRepository.findByRegistration(registration).isEmpty()){

            throw new DuplicatedDepartmentMemberException("This collaborator is already member of another department.");

        }

    }

    private void validateMultipleMembers(List<Integer> registrations){

        for(Integer registration : registrations){

            validateSingleMember(registration);
        }

    }

}
