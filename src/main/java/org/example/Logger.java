package org.example;

import org.example.appender.Appender;
import org.example.appender.ConsoleAppender;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private final List<Appender> appenders;

    public Logger() {
        appenders = new ArrayList<>();
    }

    public List<Appender> getAppenders() {
        return appenders;
    }

    public void addAppender(Appender appender) {
        appenders.add(appender);
    }

    private void log(LogLevel level, String message) {
        if (appenders.isEmpty()) {
            appenders.add(new ConsoleAppender());
        }
        for (Appender appender : appenders) {
            if (level.ordinal() >= appender.getFrom().ordinal()
                    && level.ordinal() <= appender.getTo().ordinal()) {
                String formattedMessage = appender.formattingMessage(level, message);
                appender.append(formattedMessage);
            }
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
}
