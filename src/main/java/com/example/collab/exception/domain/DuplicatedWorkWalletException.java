package com.example.collab.exception.domain;

public class DuplicatedWorkWalletException extends ConflictException {

    public DuplicatedWorkWalletException(String message) {

        super(message);

    }

}