package com.example.collab.mapper;

import org.mapstruct.*;

import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.contact.*;
import com.example.collab.domain.valueobject.document.*;
import com.example.collab.domain.model.Collaborator;
import com.example.collab.domain.model.Department;
import com.example.collab.dto.request.CollaboratorRequestDTO;
import com.example.collab.dto.response.CollaboratorResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CollaboratorMapper {

    // Pega os dados do DTO e cria uma nova entidade
    @Mapping(target = "address", source = "address")
    @Mapping(target = "position", source = "position")
    @Mapping(target = "admissionDate", source = "admissionDate")
    @Mapping(target = "contractType", source = "contractType")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "registration", source = "registration")
    @Mapping(target = "workload", source = "workload")
    @Mapping(target = "emergencyContact", source = "emergencyContact")
    @Mapping(target = "education", source = "education")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "observations", source = "observations")
    @Mapping(target = "reservistCertificate", source = "reservistCertificate")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "bank", source = "bank", qualifiedByName = "toBank")
    @Mapping(target = "agency", source = "agency", qualifiedByName = "toAgency")
    @Mapping(target = "account", source = "account", qualifiedByName = "toAccount")
    @Mapping(target = "typeAccount", source = "typeAccount", qualifiedByName = "toTypeAccount")
    @Mapping(target = "pix", source = "pix", qualifiedByName = "toPIX")
    @Mapping(target = "workWallet", source = "workWallet", qualifiedByName = "toWorkWallet")
    @Mapping(target = "voterRegistration", source = "voterRegistration", qualifiedByName = "toVoterRegistration")
    @Mapping(target = "PIS", source = "PIS", qualifiedByName = "toPIS")
    @Mapping(target = "CNH", source = "CNH", qualifiedByName = "toCNH")
    @Mapping(target = "CPF", source = "CPF", qualifiedByName = "toCPF")
    @Mapping(target = "RG", source = "RG", qualifiedByName = "toRG")
    @Mapping(target = "phoneEmergency", source = "phoneEmergency", qualifiedByName = "toPhoneEmergency")
    @Mapping(target = "department", source = "department", qualifiedByName = "toDepartment")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "supportManager", source = "supportManager")
    Collaborator toEntity(CollaboratorRequestDTO dto);

    // Pega os novos dados e atualiza a entidade existente
    @Mapping(target = "address", source = "address")
    @Mapping(target = "position", source = "position")
    @Mapping(target = "admissionDate", source = "admissionDate")
    @Mapping(target = "contractType", source = "contractType")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "registration", source = "registration")
    @Mapping(target = "workload", source = "workload")
    @Mapping(target = "emergencyContact", source = "emergencyContact")
    @Mapping(target = "education", source = "education")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "observations", source = "observations")
    @Mapping(target = "reservistCertificate", source = "reservistCertificate")
    @Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "toPhone")
    @Mapping(target = "bank", source = "bank", qualifiedByName = "toBank")
    @Mapping(target = "agency", source = "agency", qualifiedByName = "toAgency")
    @Mapping(target = "account", source = "account", qualifiedByName = "toAccount")
    @Mapping(target = "typeAccount", source = "typeAccount", qualifiedByName = "toTypeAccount")
    @Mapping(target = "pix", source = "pix", qualifiedByName = "toPIX")
    @Mapping(target = "workWallet", source = "workWallet", qualifiedByName = "toWorkWallet")
    @Mapping(target = "voterRegistration", source = "voterRegistration", qualifiedByName = "toVoterRegistration")
    @Mapping(target = "PIS", source = "PIS", qualifiedByName = "toPIS")
    @Mapping(target = "CNH", source = "CNH", qualifiedByName = "toCNH")
    @Mapping(target = "CPF", source = "CPF", qualifiedByName = "toCPF")
    @Mapping(target = "RG", source = "RG", qualifiedByName = "toRG")
    @Mapping(target = "phoneEmergency", source = "phoneEmergency", qualifiedByName = "toPhoneEmergency")
    @Mapping(target = "department", source = "department", qualifiedByName = "toDepartment")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "supportManager", source = "supportManager")
    void updateEntity(@MappingTarget Collaborator collaborator, CollaboratorRequestDTO dto);

    // Pega a entidade e converte para o DTO de resposta
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "position", source = "position")
    @Mapping(target = "admissionDate", source = "admissionDate")
    @Mapping(target = "contractType", source = "contractType")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "registration", source = "registration")
    @Mapping(target = "workload", source = "workload")
    @Mapping(target = "emergencyContact", source = "emergencyContact")
    @Mapping(target = "education", source = "education")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "reservistCertificate", source = "reservistCertificate")
    @Mapping(target = "observations", source = "observations")
    @Mapping(target = "email", source = "email", qualifiedByName = "fromEmail")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "fromPhone")
    @Mapping(target = "bank", source = "bank", qualifiedByName = "fromBank")
    @Mapping(target = "agency", source = "agency", qualifiedByName = "fromAgency")
    @Mapping(target = "account", source = "account", qualifiedByName = "fromAccount")
    @Mapping(target = "typeAccount", source = "typeAccount", qualifiedByName = "fromTypeAccount")
    @Mapping(target = "pix", source = "pix", qualifiedByName = "fromPIX")
    @Mapping(target = "workWallet", source = "workWallet", qualifiedByName = "fromWorkWallet")
    @Mapping(target = "voterRegistration", source = "voterRegistration", qualifiedByName = "fromVoterRegistration")
    @Mapping(target = "PIS", source = "PIS", qualifiedByName = "fromPIS")
    @Mapping(target = "CNH", source = "CNH", qualifiedByName = "fromCNH")
    @Mapping(target = "CPF", source = "CPF", qualifiedByName = "fromCPF")
    @Mapping(target = "RG", source = "RG", qualifiedByName = "fromRG")
    @Mapping(target = "phoneEmergency", source = "phoneEmergency", qualifiedByName = "fromPhoneEmergency")
    @Mapping(target = "department", source = "department", qualifiedByName = "fromDepartment")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "supportManager", source = "supportManager")
    CollaboratorResponseDTO toResponse(Collaborator collaborator);

    // Pega os dados e transforma em ValueObjects
    @Named("toEmail")
    default Email toEmail(String value) {

        return value != null && !value.isBlank() ? new Email(value) : null;

    }

    @Named("toPhone")
    default Phone toPhone(String value) {

        return value != null && !value.isBlank() ? new Phone(value) : null;

    }

    @Named("toPhoneEmergency")
    default Phone toPhoneEmergency(String value) {

        return value != null && !value.isBlank() ? new Phone(value) : null;

    }

    @Named("toBank")
    default Bank toBank(String value) {

        return value != null && !value.isBlank() ? new Bank(value) : null;

    }

    @Named("toAgency")
    default Agency toAgency(String value) {

        return value != null && !value.isBlank() ? new Agency(value) : null;

    }

    @Named("toAccount")
    default Account toAccount(String value) {

        return value != null && !value.isBlank() ? new Account(value) : null;

    }

    @Named("toTypeAccount")
    default TypeAccount toTypeAccount(String value) {

        return value != null && !value.isBlank() ? new TypeAccount(value) : null;

    }

    @Named("toPIX")
    default PIX toPIX(String value) {

        return value != null && !value.isBlank() ? new PIX(value) : null;

    }

    @Named("toWorkWallet")
    default WorkWallet toWorkWallet(String value) {

        return value != null && !value.isBlank() ? new WorkWallet(value) : null;

    }

    @Named("toVoterRegistration")
    default VoterRegistration toVoterRegistration(String value) {

        return value != null && !value.isBlank() ? new VoterRegistration(value) : null;

    }

    @Named("toPIS")
    default PIS toPIS(String value) {

        return value != null && !value.isBlank() ? new PIS(value) : null;

    }

    @Named("toCNH")
    default CNH toCNH(String value) {

        return value != null && !value.isBlank() ? new CNH(value) : null;

    }

    @Named("toCPF")
    default CPF toCPF(String value) {

        return value != null && !value.isBlank() ? new CPF(value) : null;

    }

    @Named("toRG")
    default RG toRG(String value) {

        return value != null && !value.isBlank() ? new RG(value) : null;

    }

    @Named("toDepartment")
    default Department toDepartment(Integer value) {

        return null;

    }

    // pega os ValueObject's e transforma em String
    @Named("fromEmail")
    default String fromEmail(Email email) {

        return email != null ? email.getEmail() : null;

    }

    @Named("fromPhone")
    default String fromPhone(Phone phone) {

        return phone != null ? phone.getNumber() : null;

    }

    @Named("fromPhoneEmergency")
    default String fromPhoneEmergency(Phone phone) {

        return phone != null ? phone.getNumber() : null;

    }

    @Named("fromBank")
    default String fromBank(Bank bank) {

        return bank != null ? bank.getCode() : null;

    }

    @Named("fromAgency")
    default String fromAgency(Agency agency) {

        return agency != null ? agency.getNumber() : null;

    }

    @Named("fromAccount")
    default String fromAccount(Account account) {

        return account != null ? account.getNumber() : null;

    }

    @Named("fromTypeAccount")
    default String fromTypeAccount(TypeAccount typeAccount) {

        return typeAccount != null ? typeAccount.getType() : null;

    }

    @Named("fromPIX")
    default String fromPIX(PIX pix) {

        return pix != null ? pix.getKey() : null;

    }

    @Named("fromWorkWallet")
    default String fromWorkWallet(WorkWallet workWallet) {

        return workWallet != null ? workWallet.getNumber() : null;

    }

    @Named("fromVoterRegistration")
    default String fromVoterRegistration(VoterRegistration voterRegistration) {

        return voterRegistration != null ? voterRegistration.getVoteId() : null;

    }

    @Named("fromPIS")
    default String fromPIS(PIS pis) {

        return pis != null ? pis.getPis() : null;

    }

    @Named("fromCNH")
    default String fromCNH(CNH cnh) {

        return cnh != null ? cnh.getCnh() : null;

    }

    @Named("fromCPF")
    default String fromCPF(CPF cpf) {

        return cpf != null ? cpf.getCpf() : null;

    }

    @Named("fromRG")
    default String fromRG(RG rg) {

        return rg != null ? rg.getRg() : null;
        
    }

    @Named("fromDepartment")
    default Integer fromDepartment(Department department) {

        return department != null ? department.getNumber() : null;

    }

}
