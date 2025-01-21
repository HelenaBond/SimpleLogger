package org.example.loggerConfigurator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Logger;
import org.example.appender.Appender;
import org.example.appender.ConsoleAppender;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        List<Appender> appenderList = logger.getAppenders();
        if (appenderList.isEmpty()) {
            appenderList.add(new ConsoleAppender());
        }
        return logger;
    }
}
