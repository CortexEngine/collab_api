package com.example.collab.domain.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import lombok.*;

@Entity
@Getter
@Setter
@Audited
public class Department {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private Integer number;

    private LocalDate initialDate;

    private LocalDate endDate;

    private Integer managerRegistration;

    @ElementCollection
    private List<Integer> managerSupportRegistration;

    @ElementCollection
    private List<Integer> teamMembersRegistration;

}
