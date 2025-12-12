package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class VoterRegistration {

    String voteId;

    public VoterRegistration(String voteId) {

        if (voteId == null || voteId.isBlank()) {

            throw new InvalidDocumentException("Voter Registration Card must be provided.");

        }

        // Remove caracteres não numéricos
        String cleanedVoteId = voteId.replaceAll("\\D", "");

        // Valida o tamanho do Título de Eleitor
        if (cleanedVoteId.length() != 12) {

            throw new InvalidDocumentException("Voter Registration Card must contain 12 digits.");

        }

        this.voteId = cleanedVoteId;

    }
}