package com.example.collab.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CollaboratorRequestDTO {

    // Identificação
    @NotBlank
    @Size(max = 120)
    private String name;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Size(max = 30)
    private String maritalStatus;

    @Size(max = 60)
    private String nationality;

    // Contato
    @Email
    @Size(max = 180)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;

    // Profissional
    @Size(max = 120)
    private String position;

    @Size(max = 120)
    private Integer department;

    @NotNull
    private boolean manager;
    
    @NotNull
    private boolean supportManager;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;

    @Size(max = 60)
    private String contractType;

    @PositiveOrZero
    private Double salary;

    @NotNull
    private Integer registration;

    @Size(max = 20)
    private String workload;

    // Bancárias
    @NotNull
    @Size(max = 3)
    private String bank;

    @NotNull
    @Size(max = 10)
    private String agency;

    @NotNull
    @Size(max = 20)
    private String account;

    @NotNull
    @Size(max = 20)
    private String typeAccount;

    @NotNull
    @Size(max = 100)
    private String pix;

    // Documentos
    @NotNull
    @Size(max = 20)
    private String workWallet;

    @Size(max = 20)
    private String voterRegistration;

    @Size(max = 40)
    private String reservistCertificate;

    @Size(max = 20)
    private String PIS;

    @Size(max = 20)
    private String CNH;

    @NotNull
    @Size(max = 14)
    private String CPF;

    @NotNull
    @Size(max = 20)
    private String RG;

    // Emergência
    @Size(max = 120)
    private String emergencyContact;

    @Size(max = 20)
    private String phoneEmergency;

    // Adicionais
    @Size(max = 60)
    private String education;

    @Size(max = 120)
    private String course;

    @Size(max = 120)
    private String observations;
}
