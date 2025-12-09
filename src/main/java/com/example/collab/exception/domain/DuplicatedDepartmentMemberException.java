package com.example.collab.exception.domain;

public class DuplicatedDepartmentMemberException extends ConflictException {

    public DuplicatedDepartmentMemberException(String message) {
     
      super(message);
    
    }

}
