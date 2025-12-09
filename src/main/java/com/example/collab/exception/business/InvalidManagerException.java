package com.example.collab.exception.business;

public class InvalidManagerException extends UnprocessableEntityException {

    public InvalidManagerException(String message) {
        super(message);
    }

}
