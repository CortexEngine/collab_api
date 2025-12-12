package com.example.collab.exception.business;

public class InvalidDepartmentException extends UnprocessableEntityException {

    public InvalidDepartmentException(String message) {

        super(message);

    }

}
