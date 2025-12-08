package com.example.collab.exception.domain;

public class DuplicatedCPFException extends ConflictException {

    public DuplicatedCPFException(String message) {

        super(message);

    }

}