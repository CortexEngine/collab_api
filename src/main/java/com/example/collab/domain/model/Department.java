package com.example.collab.domain.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Id
    @Getter
    @Setter
    @Column(unique = true)
    private Integer number;

    @Getter
    @Setter
    private LocalDate initialDate;

    @Getter
    @Setter
    private LocalDate endDate;

    @Getter
    @Setter
    private Integer managerRegistration;

    @Getter
    @Setter
    private List<Integer> managerSupportRegistration;

    @Getter
    @Setter
    private List<Integer> teamMembersRegistration;

}
