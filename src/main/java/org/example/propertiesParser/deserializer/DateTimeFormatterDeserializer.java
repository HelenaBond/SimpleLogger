package org.example.propertiesParser.deserializer;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDeserializer implements PropertiesDeserializer<DateTimeFormatter> {
    @Override
    public DateTimeFormatter deserialize(String value) {
        return DateTimeFormatter.ofPattern(value);
    }
}
