package com.example.collab.exception.domain;

public class DuplicatedNameDepartmentException extends ConflictException {

    public DuplicatedNameDepartmentException(String message) {

        super(message);

    }

}
