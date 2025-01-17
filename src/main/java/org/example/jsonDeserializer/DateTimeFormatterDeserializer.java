package org.example.jsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDeserializer extends JsonDeserializer<DateTimeFormatter> {

    @Override
    public DateTimeFormatter deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        return DateTimeFormatter.ofPattern(jsonParser.getValueAsString("yyyy-MM-dd HH:mm:ss"));
    }
}
