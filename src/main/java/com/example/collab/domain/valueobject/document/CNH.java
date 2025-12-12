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

        // Valida os dígitos verificadores
        if (!isValidCNH(cleanedCNH)) {

            throw new InvalidDocumentException("Invalid driver's license: " + cnh);

        }

        this.cnh = cleanedCNH;

    }

    private boolean isValidCNH(String cnh) {

        // Multiplicadores para o primeiro dígito verificador
        int[] weights1 = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };

        int sum1 = 0;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {

            int digit = Character.getNumericValue(cnh.charAt(i));
            sum1 += digit * weights1[i];

        }

        int remainder1 = sum1 % 11;

        int check1 = remainder1 == 10 ? 0 : remainder1;

        // Multiplicadores para o segundo dígito verificador
        int[] weights2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1 };

        int sum2 = 0;

        // Calcula o segundo dígito verificador
        for (int i = 0; i < 9; i++) {

            int digit = Character.getNumericValue(cnh.charAt(i));
            sum2 += digit * weights2[i];

        }

        sum2 += check1 * weights2[9];

        int remainder2 = sum2 % 11;

        int check2 = remainder2 == 10 ? 0 : remainder2;

        // Compara os dígitos calculados com os informados
        return check1 == Character.getNumericValue(cnh.charAt(9)) && check2 == Character.getNumericValue(cnh.charAt(10));
        
    }
}