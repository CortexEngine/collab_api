package com.example.collab.domain.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

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
    @ElementCollection
    private List<Integer> managerSupportRegistration;

    @Getter
    @Setter
    @ElementCollection
    private List<Integer> teamMembersRegistration;

}
