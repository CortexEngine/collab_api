package com.example.collab.exception.domain;

public class DuplicatedAccountException extends ConflictException {

    public DuplicatedAccountException(String message) {

        super(message);

    }

}
