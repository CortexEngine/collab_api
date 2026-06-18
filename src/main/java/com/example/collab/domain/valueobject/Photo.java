package com.example.collab.domain.valueobject;

import com.example.collab.exception.business.InvalidDocumentException;

import lombok.Value;

@Value
public class Photo {

    String path;

    public Photo(String path) {

        if (path == null || path.isBlank()) {

            throw new InvalidDocumentException("Photo path must be provided");

        }

        this.path = path;
    }
}
