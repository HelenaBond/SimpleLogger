package org.example.appenderConfigurator;

import org.example.appender.Appender;

import java.util.Properties;

public interface AppenderPropertiesConfigurator {

    Appender createAppender(Properties properties, String prefix);
    void baseConfiguration(Appender appender, Properties properties, String prefix);
}
