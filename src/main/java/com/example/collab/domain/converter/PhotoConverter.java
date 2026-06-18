package com.example.collab.domain.converter;

import com.example.collab.domain.valueobject.Photo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PhotoConverter implements AttributeConverter<Photo, String> {

    @Override
    public String convertToDatabaseColumn(Photo attribute) {

        return attribute != null ? attribute.getPath() : null;

    }

    @Override
    public Photo convertToEntityAttribute(String dbData) {

        return dbData != null && !dbData.isBlank() ? new Photo(dbData) : null;

    }
}
