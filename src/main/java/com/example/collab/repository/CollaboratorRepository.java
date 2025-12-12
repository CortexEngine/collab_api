package com.example.collab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collab.domain.model.Collaborator;
import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.document.*;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    Optional<Collaborator> findByRegistration(Integer registration);

    List<Collaborator> findByPosition(String position);

    List<Collaborator> findByContractType(String contractType);

    Optional<Collaborator> findByWorkload(String workload);

    Optional<Collaborator> findByCPF(CPF CPF);

    Optional<Collaborator> findByRG(RG RG);

    Optional<Collaborator> findByCNH(CNH CNH);

    Optional<Collaborator> findByPIS(PIS PIS);

    Optional<Collaborator> findByWorkWallet(WorkWallet workWallet);

    Optional<Collaborator> findByVoterRegistration(VoterRegistration voterRegistration);

    List<Collaborator> findByBank(Bank bank);

    Optional<Collaborator> findByAccount(Account account);

    Optional<Collaborator> findByPix(PIX pix);

    List<Collaborator> findByName(String name);

    Optional<Collaborator> findByEmail(String email);

    void deleteByRegistration(Integer registration);

    Optional<Collaborator> findByRegistrationAndManager(Integer registration, boolean manager);

    Optional<Collaborator> findByRegistrationAndSupportManager(Integer registration, boolean support_manager);

}
