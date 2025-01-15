package org.example.LoggerConfigurator;

import org.example.Logger;
import org.example.appender.Appender;
import org.example.appenderConfigurator.AppenderPropertiesConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoggerPropertiesConfigurator implements LoggerConfigurator {
    private final String pathToSettings;

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
            String className = properties.getProperty(prefix);
            try {
                AppenderPropertiesConfigurator appenderPropertiesConfigurator = (AppenderPropertiesConfigurator)
                        Class.forName(className).getDeclaredConstructor().newInstance();
                Appender appender = appenderPropertiesConfigurator.createAppender(properties, prefix);
                appenderPropertiesConfigurator.baseConfiguration(appender, properties, prefix);
                logger.addAppender(appender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logger;}

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = LoggerPropertiesConfigurator.class
                .getClassLoader()
                .getResourceAsStream(pathToSettings)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + pathToSettings);
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
