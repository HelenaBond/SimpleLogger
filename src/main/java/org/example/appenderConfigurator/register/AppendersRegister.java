package org.example.appenderConfigurator.register;

import org.example.appender.Appender;
import org.example.appender.ConsoleAppender;
import org.example.appender.FileAppender;

import java.util.HashMap;
import java.util.Map;

public class AppendersRegister implements Register {

    private final Map<String, Class<? extends Appender>> appenderTypes = new HashMap<>();
    {
        appenderTypes.put("file", FileAppender.class);
        appenderTypes.put("console", ConsoleAppender.class);
    }

    @Override
    public void registerAppender(String type, Class<? extends Appender> appender) {
        if (appenderTypes.containsKey(type)) {
            throw new IllegalArgumentException("Appender type already registered: " + type);
        }
        appenderTypes.put(type.toLowerCase(), appender);
    }

    @Override
    public Class<? extends Appender> getAppenderType(String type) {
        return appenderTypes.get(type.toLowerCase());
    }

    @Override
    public boolean containsAppender(String type) {
        return appenderTypes.containsKey(type.toLowerCase());
    }
}
