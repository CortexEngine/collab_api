package com.example.collab.domain.model;

import java.time.LocalDate;

import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.contact.*;
import com.example.collab.domain.valueobject.document.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Collaborator {

    // Informações Pessoais

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private LocalDate birthDate;

    @Getter
    @Setter
    private String maritalStatus;

    @Getter
    @Setter
    private String nationality;

    // ------------------------------------------------------------------//

    // Informações de Contato

    @Getter
    @Setter
    private Email email;

    @Getter
    @Setter
    private Phone phone;

    @Getter
    @Setter
    private String address;

    // ------------------------------------------------------------------//

    // Informações Profissionais

    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "department_number", referencedColumnName = "number")
    private Department department;

    @Getter
    @Setter
    private LocalDate admissionDate;

    @Getter
    @Setter
    private String contractType;

    @Getter
    @Setter
    private Double salary;

    @Getter
    @Setter
    private Integer registration;

    @Getter
    @Setter
    private String workload;

    @Getter
    @Setter
    private boolean manager;

    @Getter
    @Setter
    private boolean supportManager;

    // ------------------------------------------------------------------//

    // Informações Bancárias

    @Getter
    @Setter
    private Bank bank;

    @Getter
    @Setter
    private Agency agency;

    @Getter
    @Setter
    private Account account;

    @Getter
    @Setter
    private TypeAccount typeAccount;

    @Getter
    @Setter
    private PIX pix;

    // ------------------------------------------------------------------//

    // Dcocumentos

    @Getter
    @Setter
    private WorkWallet workWallet;

    @Getter
    @Setter
    private VoterRegistration voterRegistration;

    @Getter
    @Setter
    private String reservistCertificate;

    @Getter
    @Setter
    private PIS PIS;

    @Getter
    @Setter
    private CNH CNH;

    @Getter
    @Setter
    private CPF CPF;

    @Getter
    @Setter
    private RG RG;

    // ------------------------------------------------------------------//

    // Informações de Emergência

    @Getter
    @Setter
    private String emergencyContact;

    @Getter
    @Setter
    private Phone phoneEmergency;

    // ------------------------------------------------------------------//

    // Informações Adicionais

    @Getter
    @Setter
    private String education;

    @Getter
    @Setter
    private String course;

    @Getter
    @Setter
    private String observations;
}
