package org.example.loggerConfigurator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Logger;

import java.io.File;
import java.io.IOException;

public class LoggerJsonConfigurator implements LoggerConfigurator {

    private final String pathToSettings;

    public LoggerJsonConfigurator(String pathToSettings) {
        this.pathToSettings = pathToSettings;
    }

    @Override
    public Logger createLogger() {
        Logger logger = new Logger();
        ObjectMapper objectMapper = new ObjectMapper();
        File json = new File(pathToSettings);
        try {
            logger = objectMapper.readValue(json, Logger.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
