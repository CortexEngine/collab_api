package com.example.collab.exception.domain;

public class DuplicatedEmailException extends ConflictException {

    public DuplicatedEmailException(String message) {

        super(message);

    }

}