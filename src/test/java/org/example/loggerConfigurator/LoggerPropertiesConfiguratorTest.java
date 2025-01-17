package org.example.loggerConfigurator;

import org.example.LogLevel;
import org.example.Logger;
import org.example.appender.FileAppender;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoggerPropertiesConfiguratorTest {

    @Test
    public void whenFoolFileAppenderByPropertiesConfigureSuccessfully() {
        Logger logger = new LoggerPropertiesConfigurator("src/test/resources/foolLoggerTest.properties").createLogger();
        FileAppender fileAppender = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        assertThat(fileAppender.getMessageFormat()).isEqualTo("%s %s %s");
        DateTimeFormatter actualFormatter = fileAppender.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(fileAppender.getFrom()).isEqualTo(LogLevel.DEBUG);
        assertThat(fileAppender.getTo()).isEqualTo(LogLevel.WARN);
        assertThat(fileAppender.getFilePath()).isEqualTo("appenderTest.txt");
    }

    @Test
    public void whenDefaultFileAppenderByPropertiesConfigureSuccessfully() {
        Logger logger = new LoggerPropertiesConfigurator("src/test/resources/defaultLoggerTest.properties").createLogger();
        FileAppender fileAppender = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertThat(fileAppender.getMessageFormat()).isEqualTo("[%s] [%s] %s");
        DateTimeFormatter actualFormatter = fileAppender.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(fileAppender.getFrom()).isEqualTo(LogLevel.INFO);
        assertThat(fileAppender.getTo()).isEqualTo(LogLevel.ERROR);
        assertThat(fileAppender.getFilePath()).isEqualTo("log.txt");
    }
}
