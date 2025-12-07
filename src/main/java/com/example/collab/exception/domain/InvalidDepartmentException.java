package com.example.collab.exception.domain;

import com.example.collab.exception.business.UnprocessableEntityException;

public class InvalidDepartmentException extends UnprocessableEntityException {

    public InvalidDepartmentException(String message) {

        super(message);

    }

}
