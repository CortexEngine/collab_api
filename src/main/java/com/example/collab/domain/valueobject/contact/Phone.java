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

        // Formato nacional com 9 dígitos (celular): (XX) 9XXXX-XXXX
        if (number.matches("\\(\\d{2}\\)\\s?9\\d{4}-\\d{4}")) {
            return true;
        }

        // Formato nacional com 8 dígitos (fixo): (XX) XXXX-XXXX
        if (number.matches("\\(\\d{2}\\)\\s?[2-5]\\d{3}-\\d{4}")) {
            return true;
        }

        // Formato internacional com 9 dígitos: +55 XX 9XXXX-XXXX
        if (number.matches("\\+\\d{1,3}\\s?\\d{2}\\s?9\\d{4}-\\d{4}")) {
            return true;
        }

        // Formato internacional com 8 dígitos: +55 XX XXXX-XXXX
        if (number.matches("\\+\\d{1,3}\\s?\\d{2}\\s?[2-5]\\d{3}-\\d{4}")) {
            return true;
        }

        return false;
    }
}