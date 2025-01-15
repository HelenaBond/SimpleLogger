package org.example.appender;

import org.example.LogLevel;

import java.time.format.DateTimeFormatter;

public interface Formattable {

    void setDateTimeFormat(DateTimeFormatter dateTimeFormat);
    DateTimeFormatter getDateTimeFormat();
    void setMessageFormat(String messageFormat);
    String getMessageFormat();
    LogLevel getFrom();
    void setFrom(LogLevel from);
    LogLevel getTo();
    void setTo(LogLevel to);
}
