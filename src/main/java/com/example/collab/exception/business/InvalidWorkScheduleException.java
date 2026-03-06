package com.example.collab.exception.business;

public class InvalidWorkScheduleException extends UnprocessableEntityException {

    public InvalidWorkScheduleException(String message) {
        super(message);
    }

}
