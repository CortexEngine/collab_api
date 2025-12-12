package com.example.collab.domain.valueobject.banking;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class TypeAccount {

    String type;

    public TypeAccount(String type) {

        if (type == null || type.isBlank()) {

            throw new InvalidDocumentException("The account type must be specified.");

        }
        if (!type.equalsIgnoreCase("Checking") && !type.equalsIgnoreCase("Savings")
                && !type.equalsIgnoreCase("Salary")) {

            throw new InvalidDocumentException("The account type must be 'Checking', 'Savings', or 'Salary'.");

        }

        this.type = type;
    }
}