package org.example.appender;

import java.util.HashMap;
import java.util.Map;

public class AppenderRegister {

    private final Map<String, Class<? extends Appender>> appenderTypes = new HashMap<>();
    {
        appenderTypes.put("file", FileAppender.class);
        appenderTypes.put("console", ConsoleAppender.class);
    }

    public void registerAppender(String type, Class<? extends Appender> appender) {
        if (appenderTypes.containsKey(type)) {
            throw new IllegalArgumentException("Appender type already registered: " + type);
        }
        appenderTypes.put(type, appender);
    }

    public Class<? extends Appender> getAppenderType(String type) {
        return appenderTypes.get(type);
    }
}
