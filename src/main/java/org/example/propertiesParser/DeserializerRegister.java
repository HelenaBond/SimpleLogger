package org.example.propertiesParser;

import org.example.LogLevel;
import org.example.propertiesParser.deserializer.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DeserializerRegister {
    private final Map<Class<?>, Class<? extends PropertiesDeserializer<?>>> deserializers = new HashMap<>();
    {
        deserializers.put(DateTimeFormatter.class, DateTimeFormatterDeserializer.class);
        deserializers.put(String.class, StringDeserializer.class);
        deserializers.put(LogLevel.class, LogLevelDeserializer.class);
    }

    public Class<? extends PropertiesDeserializer<?>> getDeserializer(Class<?> clazz) {
        return deserializers.get(clazz);
    }
}
