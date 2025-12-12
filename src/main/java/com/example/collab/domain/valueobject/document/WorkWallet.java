package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class WorkWallet {
    
    String number;

    public WorkWallet(String number) {

        if (number == null || number.isBlank()) {

            throw new InvalidDocumentException("Work Permit must be provided.");

        }

        // Remove caracteres não numéricos
        String cleanedNumber = number.replaceAll("\\D", "");

        // Valida o tamanho da Carteira de Trabalho
        if (cleanedNumber.length() != 11) {

            throw new InvalidDocumentException("The Work Permit must contain 11 digits (7 for the number and 4 for the series).");
            
        }

        // Validação para evitar números com todos os dígitos iguais
        if (cleanedNumber.matches("(\\d)\\1+")) {

            throw new InvalidDocumentException("A work permit cannot contain all the same digits.");

        }

        this.number = cleanedNumber;

    }
}