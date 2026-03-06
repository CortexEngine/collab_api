package com.example.collab.exception.domain;

public class DuplicatedWorkTimeException extends ConflictException {

    public DuplicatedWorkTimeException(String message) {
        super(message);
    }

}
