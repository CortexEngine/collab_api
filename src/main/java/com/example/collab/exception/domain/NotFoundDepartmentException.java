package com.example.collab.exception.domain;

import com.example.collab.exception.resource.NotFoundException;

public class NotFoundDepartmentException extends NotFoundException {

    public NotFoundDepartmentException(String message) {

        super(message);

    }

}
