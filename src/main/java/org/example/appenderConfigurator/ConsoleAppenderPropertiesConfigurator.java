package org.example.appenderConfigurator;

import org.example.appender.Appender;
import org.example.appender.ConsoleAppender;

import java.util.Properties;

public class ConsoleAppenderPropertiesConfigurator extends BaseAppenderPropertiesConfigurator {

    @Override
    public Appender createAppender(Properties properties, String prefix) {
        return new ConsoleAppender();
    }
}
