package org.example.LoggerConfigurator;

import org.example.Logger;
import org.example.appender.Appender;
import org.example.appenderConfigurator.AppenderPropertiesConfigurator;
import org.example.appenderConfigurator.ConsoleAppenderPropertiesConfigurator;
import org.example.appenderConfigurator.FileAppenderPropertiesConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoggerPropertiesConfigurator implements LoggerConfigurator {
    private final String pathToSettings;
    public static final String CONSOLE = "console";
    public static final String FILE = "file";

    private final Map<String, AppenderPropertiesConfigurator> appenderConfigurators = new HashMap<>();

    {
        registerAppender(FILE, new FileAppenderPropertiesConfigurator());
        registerAppender(CONSOLE, new ConsoleAppenderPropertiesConfigurator());
    }

    public LoggerPropertiesConfigurator(String pathToSettings) {
        this.pathToSettings = pathToSettings;
    }

    @Override
    public Logger createLogger() {
        Properties properties = readProperties();
        Logger logger = new Logger();
        String[] appenders = properties.getProperty("log.appender.names").split("\\s*,\\s*");
        for (String appenderName : appenders) {
                String prefix = "log.appender.configurator.%s".formatted(appenderName);
                String type = properties.getProperty("%s.type".formatted(prefix));
                AppenderPropertiesConfigurator configurator = appenderConfigurators.get(type.toLowerCase());
                if (configurator == null) {
                    System.err.println("Unknown appender type: " + type);
                    continue;
                }
                try {
                    Appender appender = configurator.createAppender(properties, prefix);
                    configurator.baseConfiguration(appender, properties, prefix);
                    logger.addAppender(appender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = LoggerPropertiesConfigurator.class
                .getClassLoader()
                .getResourceAsStream(pathToSettings)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void registerAppender(String type, AppenderPropertiesConfigurator configurator) {
        if (appenderConfigurators.containsKey(type)) {
            throw new IllegalArgumentException("Appender type already registered: " + type);
        }
        appenderConfigurators.put(type, configurator);
    }
}
