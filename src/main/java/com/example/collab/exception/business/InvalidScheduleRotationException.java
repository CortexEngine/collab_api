package com.example.collab.exception.business;

public class InvalidScheduleRotationException extends UnprocessableEntityException {

    public InvalidScheduleRotationException(String message) {
        super(message);
    }

}
