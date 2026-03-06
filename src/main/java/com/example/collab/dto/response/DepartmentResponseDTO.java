package com.example.collab.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record DepartmentResponseDTO(

    String name,

    Integer number,
    @JsonFormat(pattern = "yyyy-MM-dd")
     LocalDate initialDate,

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate,

    Integer managerRegistration,

    List<Integer> managerSupportRegistration,

    List<Integer> teamMembersRegistration
    
) {};
