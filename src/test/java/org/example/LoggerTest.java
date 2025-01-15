package org.example;

import org.example.appender.Appender;
import org.example.appender.ConsoleAppender;
import org.example.appender.FileAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoggerTest {
    private Logger logger;
    private Path defaultPath;

    @BeforeEach
    public void setUp() {
        String path = "loggerTest.txt";
        defaultPath = Path.of(path);

        logger = new Logger();
        Appender consoleAppender = new ConsoleAppender();
        logger.addAppender(consoleAppender);
        Appender fileAppender = new FileAppender(path);
        fileAppender.setFrom(LogLevel.DEBUG);
        fileAppender.setTo(LogLevel.WARN);
        logger.addAppender(fileAppender);
    }

    @Test
    public void testDebugLogging() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String expected = "This is a debug message";
        logger.debug(expected);
        assertThat(outputStream.toString()).contains(expected);
        assertThat(outputStream.toString()).contains("DEBUG");
        System.setOut(originalOut);
        assertThat(Files.readAllLines(defaultPath).toString()
                .contains(expected))
                .isTrue();
        Files.delete(defaultPath);
    }

    @Test
    public void testInfoLogging() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String expected = "This is an info message";
        logger.info(expected);
        assertThat(outputStream.toString()).contains(expected);
        assertThat(outputStream.toString()).contains("INFO");
        System.setOut(originalOut);
    }

    @Test
    public void testWarnLogging() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String expected = "This is a warn message";
        logger.warn(expected);
        assertThat(outputStream.toString()).contains(expected);
        assertThat(outputStream.toString()).contains("WARN");
        System.setOut(originalOut);
        assertThat(Files.readAllLines(defaultPath).toString()
                .contains(expected))
                .isTrue();
        Files.delete(defaultPath);
    }

    @Test
    public void testErrorLogging() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String expected = "This is an error message";
        logger.error(expected);
        assertThat(outputStream.toString()).contains(expected);
        assertThat(outputStream.toString()).contains("ERROR");
        System.setOut(originalOut);
        assertThat(Files.notExists(defaultPath)).isTrue();
    }
}
