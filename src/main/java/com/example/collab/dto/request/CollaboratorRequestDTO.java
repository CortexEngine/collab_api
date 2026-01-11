package com.example.collab.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;

public record CollaboratorRequestDTO(

    @NotBlank
    @Size(max = 120)
    String name,

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,

    @Size(max = 30)
    String maritalStatus,

    @Size(max = 60)
    String nationality,

    @Email
    @Size(max = 180)
    String email,

    @Size(max = 20)
    String phone,

    @Size(max = 255)
    String address,

    @Size(max = 120)
    String position,

    Integer department,

    @NotNull
    boolean manager,
    
    @NotNull
    boolean supportManager,

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate admissionDate,

    @Size(max = 60)
    String contractType,

    @PositiveOrZero
    Double salary,

    @NotNull
    Integer registration,

    @Size(max = 20)
    Integer workSchedule,

    @NotNull
    @Size(max = 3)
    String bank,

    @NotNull
    @Size(max = 10)
    String agency,

    @NotNull
    @Size(max = 20)
    String account,

    @NotNull
    @Size(max = 20)
    String typeAccount,

    @NotNull
    @Size(max = 100)
    String pix,

    @NotNull
    @Size(max = 20)
    String workWallet,

    @Size(max = 20)
    String voterRegistration,

    @Size(max = 40)
    String reservistCertificate,

    @Size(max = 20)
    String PIS,

    @Size(max = 20)
    String CNH,

    @NotNull
    @Size(max = 14)
    String CPF,

    @NotNull
    @Size(max = 20)
    String RG,

    @Size(max = 120)
    String emergencyContact,

    @Size(max = 20)
    String phoneEmergency,

    @Size(max = 60)
    String education,

    @Size(max = 120)
    String course,

    @Size(max = 120)
    String observations
) {};
