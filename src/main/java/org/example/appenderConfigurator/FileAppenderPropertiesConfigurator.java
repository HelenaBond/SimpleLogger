package org.example.appenderConfigurator;

import org.example.appender.Appender;
import org.example.appender.FileAppender;

import java.util.Properties;

public class FileAppenderPropertiesConfigurator extends BaseAppenderPropertiesConfigurator {

    @Override
    public Appender createAppender(Properties properties, String prefix) {
        FileAppender fileAppender = new FileAppender();
        String filePath = properties.getProperty("%s.path".formatted(prefix), "log.txt");
        fileAppender.setFilePath(filePath);
        return fileAppender;
    }
}
