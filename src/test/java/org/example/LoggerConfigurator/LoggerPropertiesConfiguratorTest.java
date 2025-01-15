package org.example.LoggerConfigurator;

import org.example.LogLevel;
import org.example.Logger;
import org.example.appender.FileAppender;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoggerPropertiesConfiguratorTest {

    @Test
    public void whenFoolFileAppenderConfigureSuccessfully() {
        Logger logger = new LoggerPropertiesConfigurator("foolLoggerTest.properties").createLogger();
        FileAppender actualLogger = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        assertThat(actualLogger.getMessageFormat()).isEqualTo("%s %s %s");
        DateTimeFormatter actualFormatter = actualLogger.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(actualLogger.getFrom()).isEqualTo(LogLevel.DEBUG);
        assertThat(actualLogger.getTo()).isEqualTo(LogLevel.WARN);
        assertThat(actualLogger.getFilePath()).isEqualTo("appenderTest.txt");
    }

    @Test
    public void whenDefaultFileAppenderConfigureSuccessfully() {
        Logger logger = new LoggerPropertiesConfigurator("defaultLoggerTest.properties").createLogger();
        FileAppender actualLogger = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(actualLogger.getMessageFormat()).isEqualTo("[%s] [%s] %s");
        DateTimeFormatter actualFormatter = actualLogger.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(actualLogger.getFrom()).isEqualTo(LogLevel.INFO);
        assertThat(actualLogger.getTo()).isEqualTo(LogLevel.ERROR);
        assertThat(actualLogger.getFilePath()).isEqualTo("log.txt");
    }
}
