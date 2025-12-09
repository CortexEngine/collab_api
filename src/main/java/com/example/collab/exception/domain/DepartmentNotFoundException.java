package com.example.collab.exception.domain;

import com.example.collab.exception.resource.NotFoundException;

public class DepartmentNotFoundException extends NotFoundException {

    public DepartmentNotFoundException(String message) {
        super(message);
    }

}
