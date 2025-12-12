package com.example.collab.domain.valueobject.document;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class CPF {

    String cpf;

    public CPF(String cpf) {

        // Validação de valores em branco ou vazios
        if (cpf == null || cpf.isBlank()) {

            throw new InvalidDocumentException("CPF must be provided");

        }

        // Limpa a formatação
        String cleanedCPF = cpf.replaceAll("\\D", "");

        // Validação de tamanho do CPF
        if (cleanedCPF.length() != 11) {

            throw new InvalidDocumentException("CPF must contain 11 digits");

        }

        // Validação de digitos iguais
        if (cleanedCPF.matches("(\\d)\\1{10}")) {

            throw new InvalidDocumentException("CPF must not contain all identical digits");

        }

        // Valida os dígitos verificadores
        if (!isValidCPF(cleanedCPF)) {

            throw new InvalidDocumentException("Invalid CPF: " + cpf);

        }

        this.cpf = cleanedCPF;

    }

    private boolean isValidCPF(String cpf) {

        try {
            // Cálculo do primeiro dígito verificador
            int sum1 = 0;

            for (int i = 0; i < 9; i++) {

                int digit = Character.getNumericValue(cpf.charAt(i));

                sum1 += digit * (10 - i);

            }

            int remainder1 = sum1 % 11;

            int check1 = remainder1 < 2 ? 0 : 11 - remainder1;

            // Cálculo do segundo dígito verificador
            int sum2 = 0;

            for (int i = 0; i < 9; i++) {

                int digit = Character.getNumericValue(cpf.charAt(i));

                sum2 += digit * (11 - i);

            }

            sum2 += check1 * 2;

            int remainder2 = sum2 % 11;

            int check2 = remainder2 < 2 ? 0 : 11 - remainder2;

            // Compara os dígitos calculados com os dígitos informados
            return check1 == Character.getNumericValue(cpf.charAt(9)) && check2 == Character.getNumericValue(cpf.charAt(10));

        } catch (NumberFormatException e) {

            return false;
            
        }
    }

}
