package org.example.appenderConfigurator;

import org.example.LogLevel;
import org.example.appender.Appender;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

public abstract class BaseAppenderPropertiesConfigurator implements AppenderPropertiesConfigurator {

    public void baseConfiguration(Appender appender, Properties properties, String prefix) {
        String messageFormat = properties.getProperty("%s.message_format".formatted(prefix));
        if (messageFormat != null) {
            appender.setMessageFormat(messageFormat);
        }
        String dateTimeFormat = properties.getProperty("%s.datetime_format".formatted(prefix));
        if (dateTimeFormat != null) {
            try {
                appender.setDateTimeFormat(DateTimeFormatter.ofPattern(dateTimeFormat));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        String levelFrom = properties.getProperty("%s.level_from".formatted(prefix));
        if (levelFrom != null) {
            try {
                appender.setFrom(LogLevel.valueOf(levelFrom));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        String levelTo = properties.getProperty("%s.level_to".formatted(prefix));
        if (levelTo != null) {
            try {
                appender.setTo(LogLevel.valueOf(levelTo));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
