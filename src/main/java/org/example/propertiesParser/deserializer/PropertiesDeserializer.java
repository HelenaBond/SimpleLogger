package org.example.propertiesParser.deserializer;

public interface PropertiesDeserializer<T> {
    T deserialize(String value);
}
