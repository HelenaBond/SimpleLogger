package org.example.stub;

import org.example.propertiesParser.deserializer.PropertiesDeserializer;

public class IntegerDeserializerStub implements PropertiesDeserializer<Integer> {
    @Override
    public Integer deserialize(String value) {
        return Integer.valueOf(value);
    }
}
