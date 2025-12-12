package com.example.collab.exception.domain;

public class DuplicatedVoteRegistrationException extends ConflictException {

    public DuplicatedVoteRegistrationException(String message) {

        super(message);

    }

}
