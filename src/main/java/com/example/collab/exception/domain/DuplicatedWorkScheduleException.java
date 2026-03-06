package com.example.collab.exception.domain;

public class DuplicatedWorkScheduleException extends ConflictException {

    public DuplicatedWorkScheduleException(String message) {
        super(message);
    }

}
