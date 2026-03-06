package com.example.collab.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

public record DepartmentRequestDTO(

    @NotNull
    @NotBlank
    String name,

    @NotNull
    @Positive
    Integer number,

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate initialDate,

    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate,

    @Positive
    Integer managerRegistration,
    
    List<Integer> managerSupportRegistration,

    List<Integer> teamMembersRegistration
    
) {};
