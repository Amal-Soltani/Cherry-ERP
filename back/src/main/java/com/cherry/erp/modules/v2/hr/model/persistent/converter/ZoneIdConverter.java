package com.cherry.erp.modules.v2.hr.model.persistent.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;

@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId attribute) {
        return attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String dbData) {
        return ZoneId.of(dbData);
    }

}