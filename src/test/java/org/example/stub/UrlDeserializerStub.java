package org.example.stub;

import org.example.propertiesParser.deserializer.PropertiesDeserializer;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlDeserializerStub implements PropertiesDeserializer<URL> {
    @Override
    public URL deserialize(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
