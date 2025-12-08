package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class Account {

    String number;

    public Account(String number) {

        if (number == null || number.isBlank()) {

            throw new InvalidDocumentException("Account number must be provided.");

        }

        if (!number.matches("\\d{6,12}(-\\d)?")) {

            throw new InvalidDocumentException(
                    "The account number must have between 6 and 12 digits, and may include a check digit (e.g., 123456-7).");

        }

        this.number = number;

    }
}