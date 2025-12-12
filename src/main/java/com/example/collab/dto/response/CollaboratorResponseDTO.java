package com.example.collab.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CollaboratorResponseDTO {

    // Gerados pelo sistema
    private Long id;

    // Identificação
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String maritalStatus;
    private String nationality;

    // Contato
    private String email;
    private String phone;
    private String address;

    // Profissional
    private String position;
    private Integer department;
    private boolean manager;
    private boolean supportManager;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;

    private String contractType;
    private Double salary; 
    private Integer registration;
    private String workload;

    // Bancárias 
    private String bank;
    private String agency;
    private String account;
    private String typeAccount;
    private String pix;

    // Documentos
    private String workWallet;
    private String voterRegistration;
    private String reservistCertificate;
    private String PIS;
    private String CNH;
    private String CPF;
    private String RG;

    // Emergência
    private String emergencyContact;
    private String phoneEmergency;

    // Adicionais
    private String education;
    private String course;
    private String observations;
}
