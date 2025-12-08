package com.example.collab.exception.domain;

import com.example.collab.exception.business.UnprocessableEntityException;

public class InvalidDocumentException extends UnprocessableEntityException {

    public InvalidDocumentException(String message) {

        super(message);

    }
}