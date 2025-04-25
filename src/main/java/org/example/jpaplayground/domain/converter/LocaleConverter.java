package org.example.jpaplayground.domain.converter;

import org.example.jpaplayground.domain.LocaleCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class LocaleConverter implements AttributeConverter<LocaleCode, String> {

    @Override
    public String convertToDatabaseColumn(LocaleCode attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public LocaleCode convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LocaleCode.find(dbData);
    }
}