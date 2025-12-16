package com.example.collab.domain.valueobject.contact;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class Phone {

    String number;

    public Phone(String number) {

        if (number == null || number.isBlank()) {

            throw new InvalidDocumentException("Phone number must be provided");

        }

        if (!isValidPhone(number)) {

            throw new InvalidDocumentException("Invalid phone number: " + number);

        }

        this.number = number;

    }

    private boolean isValidPhone(String number) {

        // Valida formato nacional: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
        if (number.matches("\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}")) {

            return true;

        }

        // Valida formato internacional: +55 XX XXXXX-XXXX
        if (number.matches("\\+\\d{1,3}\\s?\\d{2}\\s?\\d{4,5}-\\d{4}")) {

            return true;

        }

        return false;

    }
}