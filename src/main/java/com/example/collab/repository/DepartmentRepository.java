package com.example.collab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    Optional<Department> findByNumber(Integer number);

    Optional<Department> findByName(String name);

    List<Department> findByManagerRegistration(Integer managerRegistration);

    List<Department> findByManagerSupportRegistrationContains(Integer managerSupportRegistration);

    List<Department> findByTeamMembersRegistrationIn(List<Integer> teamMembersRegistration);

}