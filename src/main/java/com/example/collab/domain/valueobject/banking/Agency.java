package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class Agency {
    String number;

    public Agency(String number) {

        if (number == null || number.isBlank()) {

            throw new InvalidDocumentException("The agency number must be provided.");

        }
        if (!number.matches("\\d{4}(-\\d)?")) {

            throw new InvalidDocumentException(
                    "The agency number must be 4 digits long and may include a check digit (e.g., 1234-5)");

        }

        this.number = number;
    }
}