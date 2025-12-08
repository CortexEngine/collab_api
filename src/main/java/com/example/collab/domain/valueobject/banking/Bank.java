package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class Bank {

    String code;

    public Bank(String code) {

        if (code == null || code.isBlank()) {

            throw new InvalidDocumentException("The bank code must be provided.");

        }
        if (!code.matches("\\d{3}")) {

            throw new InvalidDocumentException("The bank code must contain exactly 3 digits.");

        }

        this.code = code;
    }
}