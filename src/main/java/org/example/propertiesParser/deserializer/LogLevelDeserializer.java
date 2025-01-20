package org.example.propertiesParser.deserializer;

import org.example.LogLevel;

public class LogLevelDeserializer implements PropertiesDeserializer<LogLevel> {
    @Override
    public LogLevel deserialize(String value) {
        return LogLevel.valueOf(value);
    }
}
