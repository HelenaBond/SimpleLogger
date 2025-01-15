package org.example.appender;

public class ConsoleAppender extends AbstractAppender {

    @Override
    public void append(String message) {
        System.out.println(message);
    }
}
