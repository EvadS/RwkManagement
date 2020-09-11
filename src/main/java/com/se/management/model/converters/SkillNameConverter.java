package com.se.management.model.converters;

import com.se.management.model.enums.SkillName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SkillNameConverter implements AttributeConverter<SkillName, String> {
    @Override
    public String convertToDatabaseColumn(SkillName paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }
        return paymentMethod.getName();
    }

    @Override
    public SkillName convertToEntityAttribute(String id) {
        return SkillName.of(id);
    }
}