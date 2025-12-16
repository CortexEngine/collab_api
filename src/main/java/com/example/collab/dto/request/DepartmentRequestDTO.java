package com.example.collab.dto.request;

import java.time.LocalDate;
import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;

@Data
public class DepartmentRequestDTO{

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer number;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initialDate;

    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Positive
    private Integer managerRegistration;
    
    private List<Integer> managerSupportRegistration;

    private List<Integer> teamMembersRegistration;
    
}
