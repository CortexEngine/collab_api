package com.example.collab.exception.domain;

public class DuplicatedScheduleRotationException extends ConflictException {

    public DuplicatedScheduleRotationException(String message) {
        super(message);
    }

}
