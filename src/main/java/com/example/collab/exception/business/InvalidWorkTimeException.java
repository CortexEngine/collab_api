package com.example.collab.exception.business;

public class InvalidWorkTimeException extends UnprocessableEntityException {

    public InvalidWorkTimeException(String message) {
        super(message);
    }

}
