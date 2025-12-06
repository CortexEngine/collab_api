package com.example.collab.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponseDTO{

    private String name;

    private Integer number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate initialDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer managerRegistration;

    private List<Integer> managerSupportRegistration;

    private List<Integer> teamMembersRegistration;
}
