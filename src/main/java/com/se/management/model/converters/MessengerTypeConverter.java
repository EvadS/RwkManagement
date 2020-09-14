package com.se.management.model.converters;

import com.se.management.model.enums.MessengerType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MessengerTypeConverter implements AttributeConverter<MessengerType, String> {
    @Override
    public String convertToDatabaseColumn(MessengerType messengerType) {
        if (messengerType == null) {
            return null;
        }
        return messengerType.getName();
    }

    @Override
    public MessengerType convertToEntityAttribute(String id) {
        return MessengerType.of(id);
    }
}