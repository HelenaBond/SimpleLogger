package org.example.propertiesParser.deserializer;

public class StringDeserializer implements PropertiesDeserializer<String> {
    @Override
    public String deserialize(String value) {
        return value;
    }
}
