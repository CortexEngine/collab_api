package com.example.collab.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.collab.domain.valueobject.banking.*;
import com.example.collab.domain.valueobject.document.*;
import com.example.collab.exception.business.InvalidCollaboratorException;
import com.example.collab.exception.domain.*;
import com.example.collab.repository.CollaboratorRepository;

@Component
public class CollaboratorValidator {

    
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    public CollaboratorValidator(CollaboratorRepository collaboratorRepository){

        this.collaboratorRepository = collaboratorRepository;

    }

    public void validateNewCollaboratorDocuments(String cpf, String rg, String cnh, String pis, String workWallet,
            String voterRegistration) {

        if (cpf != null && !cpf.isBlank()) {

            CPF cpfObj = new CPF(cpf);

            if (collaboratorRepository.findByCPF(cpfObj).isPresent()) {

                throw new DuplicatedCPFException("CPF already exists");

            }
        }

        if (rg != null && !rg.isBlank()) {

            RG rgObj = new RG(rg);

            if (collaboratorRepository.findByRG(rgObj).isPresent()) {

                throw new DuplicatedRGException("RG already exists");

            }
        }

        if (cnh != null && !cnh.isBlank()) {

            CNH cnhObj = new CNH(cnh);

            if (collaboratorRepository.findByCNH(cnhObj).isPresent()) {

                throw new DuplicatedCNHException("CNH already exists");

            }
        }

        if (pis != null && !pis.isBlank()) {

            PIS pisObj = new PIS(pis);

            if (collaboratorRepository.findByPIS(pisObj).isPresent()) {

                throw new DuplicatedPISException("PIS already exists");

            }
        }

        if (workWallet != null && !workWallet.isBlank()) {

            WorkWallet workWalletObj = new WorkWallet(workWallet);

            if (collaboratorRepository.findByWorkWallet(workWalletObj).isPresent()) {

                throw new DuplicatedWorkWalletException("Work Wallet already exists");

            }
        }

        if (voterRegistration != null && !voterRegistration.isBlank()) {

            VoterRegistration voterRegObj = new VoterRegistration(voterRegistration);

            if (collaboratorRepository.findByVoterRegistration(voterRegObj).isPresent()) {

                throw new DuplicatedVoteRegistrationException("Vote Registration Title already exists");

            }
        }

    }

    public void validateNewCollaboratorBank(String account, String pix) {

        if (account != null && !account.isBlank()) {

            Account accountObj = new Account(account);

            if (collaboratorRepository.findByAccount(accountObj).isPresent()) {

                throw new DuplicatedAccountException("Account already exists");

            }
        }

        if (pix != null && !pix.isBlank()) {

            PIX pixObj = new PIX(pix);

            if (collaboratorRepository.findByPix(pixObj).isPresent()) {

                throw new DuplicatedPixException("Pix already exists");

            }
        }

    }

    public void validateNewCollaboratorData(Integer registration) {

        if (registration != null) {

            if (collaboratorRepository.findByRegistration(registration).isPresent()) {

                throw new DuplicatedAccountException("Registration already exists");

            }
        }

    }

    public void validateCollaboratorManager(Integer registration){
        
        if(registration != null){

            if(collaboratorRepository.findByRegistrationAndSupportManager(registration, true).isPresent()) {

                    throw new InvalidCollaboratorException("Collaborator not do Manager and Suppor Manager in same time"); 

            }

        }

    }

}
