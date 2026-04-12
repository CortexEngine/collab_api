package com.example.collab.domain.model;

import java.time.LocalDate;

import org.hibernate.envers.Audited;

import com.example.collab.domain.valueobject.*;
import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.contact.*;
import com.example.collab.domain.valueobject.document.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Audited
public class Collaborator {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String maritalStatus;

    private String nationality;

    private Email email;

    private Phone phone;

    private String address;

    private String position;

    @ManyToOne
    @JoinColumn(name = "department_number", referencedColumnName = "number")
    private Department department;

    private LocalDate admissionDate;

    private ContractType contractType;

    private CollaboratorStatus status;

    private Double salary;

    private Integer registration;

    private Integer workSchedule;

    private Boolean manager;

    private Boolean supportManager;

    private Bank bank;

    private Agency agency;

    private Account account;

    private TypeAccount typeAccount;

    private PIX pix;

    private WorkWallet workWallet;

    private VoterRegistration voterRegistration;

    private String reservistCertificate;

    private PIS PIS;

    private CNH CNH;

    private CPF CPF;

    private RG RG;

    private String emergencyContact;

    private Phone phoneEmergency;

    private String education;

    private String course;

    private String observations;
}
