package com.example.collab.exception.domain;

import com.example.collab.exception.CollabApiException;

public class ConflictException extends CollabApiException {

    public ConflictException(String message) {

        super(message);
        
    }
}
