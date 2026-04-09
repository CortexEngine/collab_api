package com.example.collab.domain.converter;

import com.example.collab.domain.valueobject.ContractType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ContractTypeConverter implements AttributeConverter<ContractType, String> {

    @Override
    public String convertToDatabaseColumn(ContractType attribute) {

        return attribute != null ? attribute.getType() : null;

    }

    @Override
    public ContractType convertToEntityAttribute(String dbData) {

        return dbData != null && !dbData.isBlank() ? new ContractType(dbData) : null;

    }
    
}
