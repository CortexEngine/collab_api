package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class CNH {

    String cnh;

    public CNH(String cnh) {
        if (cnh == null || cnh.isBlank()) {
            throw new InvalidDocumentException("Driver's license must be provided");
        }

        // Remove caracteres não numéricos
        String cleanedCNH = cnh.replaceAll("\\D", "");

        // Valida o tamanho da CNH
        if (cleanedCNH.length() != 11) {
            throw new InvalidDocumentException("Driver's license must contain 11 digits");
        }

        // Valida se não são todos dígitos iguais
        if (cleanedCNH.matches("(\\d)\\1{10}")) {
            throw new InvalidDocumentException("Invalid driver's license: cannot have all equal digits");
        }

        this.cnh = cleanedCNH;

    }
}