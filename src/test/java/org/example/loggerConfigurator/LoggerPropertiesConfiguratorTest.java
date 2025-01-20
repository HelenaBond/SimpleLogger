package org.example.loggerConfigurator;

import org.example.LogLevel;
import org.example.Logger;
import org.example.appender.AppenderRegister;
import org.example.appender.FileAppender;
import org.example.stub.AppenderUrlStub;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoggerPropertiesConfiguratorTest {

    @Test
    public void whenFoolFileAppenderByPropertiesConfigureSuccessfully() {
        AppenderRegister appenderRegister = new AppenderRegister();
        LoggerConfigurator loggerConfigurator = new LoggerPropertiesConfigurator(
                "src/test/resources/foolLoggerTest.properties",
                appenderRegister);
        Logger logger = loggerConfigurator.createLogger();
        FileAppender fileAppender = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        DateTimeFormatter actualFormatter = fileAppender.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(fileAppender.getMessageFormat()).isEqualTo("%s %s %s");
        assertThat(fileAppender.getFrom()).isEqualTo(LogLevel.DEBUG);
        assertThat(fileAppender.getTo()).isEqualTo(LogLevel.WARN);
        assertThat(fileAppender.getFilePath()).isEqualTo("appenderTest.txt");
    }

    @Test
    public void whenDefaultFileAppenderByPropertiesConfigureSuccessfully() {
        AppenderRegister appenderRegister = new AppenderRegister();
        LoggerConfigurator loggerConfigurator = new LoggerPropertiesConfigurator(
                "src/test/resources/defaultLoggerTest.properties",
                appenderRegister);
        Logger logger = loggerConfigurator.createLogger();
        FileAppender fileAppender = (FileAppender) logger.getAppenders().get(0);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter actualFormatter = fileAppender.getDateTimeFormat();
        assertThat(actualFormatter.format(LocalDateTime.now()))
                .isEqualTo(expectedFormatter.format(LocalDateTime.now()));
        assertThat(fileAppender.getMessageFormat()).isEqualTo("[%s] [%s] %s");
        assertThat(fileAppender.getFrom()).isEqualTo(LogLevel.INFO);
        assertThat(fileAppender.getTo()).isEqualTo(LogLevel.ERROR);
        assertThat(fileAppender.getFilePath()).isEqualTo("log.txt");
    }

    @Test
    public void whenAddCustomAppenderUrlStubWithCustomPropertiesDeserializers() throws MalformedURLException {
        AppenderRegister register = new AppenderRegister();
        register.registerAppender("stubUrl", AppenderUrlStub.class);
        LoggerConfigurator loggerConfigurator = new LoggerPropertiesConfigurator(
                "src/test/resources/customAppenderTest.properties",
                register);
        Logger logger = loggerConfigurator.createLogger();
        AppenderUrlStub stub = (AppenderUrlStub) logger.getAppenders().get(0);
        assertThat(stub.getUrl()).isEqualTo(new URL(("http://localhost:8080")));
        assertThat(stub.getPort()).isEqualTo(8080);
        assertThat(stub.getFrom()).isEqualTo(LogLevel.INFO);
    }
}
