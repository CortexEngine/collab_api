package com.example.collab.exception.business;

public class InvalidDocumentException extends UnprocessableEntityException {

    public InvalidDocumentException(String message) {

        super(message);

    }
}