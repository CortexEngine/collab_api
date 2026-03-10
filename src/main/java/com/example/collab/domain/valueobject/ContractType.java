package com.example.collab.domain.valueobject;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class ContractType {

    String type;

    public ContractType(String type) {

        if (type == null || type.isBlank()) {

            throw new InvalidDocumentException("The contract type must be specified.");

        }
        if (!type.equalsIgnoreCase("Hourly") && !type.equalsIgnoreCase("Monthly") && !type.equalsIgnoreCase("Per task") && !type.equalsIgnoreCase("Per project")) {

            throw new InvalidDocumentException("The contract type must be 'Hourly', 'Monthly', 'Per task', or 'Per project'.");

        }

        this.type = type;
    }
}