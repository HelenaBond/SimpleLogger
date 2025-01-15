package org.example.appender;

import org.example.LogLevel;

public interface Appender extends Formattable {

    void append(String message);
    String formattingMessage(LogLevel level, String message);
}
