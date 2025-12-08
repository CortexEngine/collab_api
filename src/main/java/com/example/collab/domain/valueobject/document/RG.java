package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class RG {

    String rg;

    public RG(String rg) {

        // Validação de valores em branco ou vazios
        if (rg == null || rg.isBlank()) {

            throw new InvalidDocumentException("RG must be provided");

        }

        // Limpa a formatação
        String cleanedRG = rg.replaceAll("\\D", "");

        // Validação de tamanho do RG
        if (cleanedRG.length() < 5 || cleanedRG.length() > 12) {

            throw new InvalidDocumentException("RG must have between 5 and 12 digits after formatting");

        }

        // Validação de digitos iguais
        if (cleanedRG.matches("(\\d)\\1+")) {

            throw new InvalidDocumentException("RG must not contain all identical digits");

        }

        this.rg = cleanedRG;
        
    }
}
