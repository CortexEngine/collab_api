package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class PIS {

    String pis;

    public PIS(String pis) {

        if (pis == null || pis.isBlank()) {

            throw new InvalidDocumentException("PIS must be provided");
            
        }

        // Remove caracteres não numéricos
        String cleanedPIS = pis.replaceAll("\\D", "");

        // Valida o tamanho do PIS
        if (cleanedPIS.length() != 11) {

            throw new InvalidDocumentException("PIS must contain 11 digits");

        }

        // Valida o dígito verificador
        if (!isValidPIS(cleanedPIS)) {

            throw new InvalidDocumentException("Invalid PIS: " + pis);

        }

        this.pis = cleanedPIS;

    }

    private boolean isValidPIS(String pis) {

        int[] weights = { 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        int sum = 0;

        // Multiplica os 10 primeiros dígitos pelos pesos
        for (int i = 0; i < 10; i++) {

            int digit = Character.getNumericValue(pis.charAt(i));

            sum += digit * weights[i];

        }

        // Calcula o dígito verificador
        int remainder = sum % 11;

        int checkDigit = remainder < 2 ? 0 : 11 - remainder;

        // Compara o dígito calculado com o último dígito do PIS
        return checkDigit == Character.getNumericValue(pis.charAt(10));
        
    }
}