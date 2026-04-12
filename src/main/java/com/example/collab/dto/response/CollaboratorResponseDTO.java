package com.example.collab.dto.response;

import java.time.LocalDate;

import com.example.collab.domain.valueobject.CollaboratorStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public record CollaboratorResponseDTO (

    Long id,

    String name,

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,

    String maritalStatus,

    String nationality,

    String email,

    String phone,

    String address,

    String position,

    Integer department,

    Boolean manager,

    Boolean supportManager,

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate admissionDate,

    String contractType,

    CollaboratorStatus status,

    Double salary, 

    Integer registration,

    Integer workSchedule,

    String bank,

    String agency,

    String account,

    String typeAccount,

    String pix,

    String workWallet,

    String voterRegistration,

    String reservistCertificate,

    String PIS,

    String CNH,

    String CPF,

    String RG,

    String emergencyContact,

    String phoneEmergency,

    String education,

    String course,

    String observations

) {};
