package com.example.collab.domain.valueobject.banking;

import com.example.collab.domain.valueobject.contact.Email;
import com.example.collab.domain.valueobject.contact.Phone;
import com.example.collab.domain.valueobject.document.CNPJ;
import com.example.collab.domain.valueobject.document.CPF;
import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class PIX {

    String key;

    public PIX(String key) {

        if (key == null || key.isBlank()) {

            throw new InvalidDocumentException("PIX key must be provided.");

        }
        if (!isValidPix(key)) {

            throw new InvalidDocumentException("Invalid PIX key");

        }

        this.key = key;

    }

    private boolean isValidPix(String key) {

        // Valida CPF
        if (key.matches("\\d{11}")) {

            return new CPF(key).getCpf() != null; // Reutiliza validação de CPF

        }

        // Valida CNPJ
        if (key.matches("\\d{14}")) {

            return new CNPJ(key).getCnpj() != null;// Reutiliza validação de CNPJ

        }

        // Valida e-mail
        if (key.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {

            return new Email(key).getEmail() != null; // Reutiliza validação de e-mail

        }

        // Valida telefone
        if (key.matches("\\+?\\d{1,3}\\d{10,11}")) {

            return new Phone(key).getNumber() != null; // Reutiliza validação de telefone

        }

        // Valida chave aleatória (UUID)
        return key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    }
}