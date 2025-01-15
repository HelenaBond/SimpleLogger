package org.example;

public class Logger {
    private String name;
    private LogLevel level;

    public Logger(String name, LogLevel level) {
        this.name = name;
        this.level = level;
    }

    public void log(LogLevel level, String message) {
        // Реализация логгирования
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
