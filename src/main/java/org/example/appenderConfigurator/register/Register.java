package org.example.appenderConfigurator.register;

import org.example.appender.Appender;

public interface Register {

    void registerAppender(String type, Class<? extends Appender> appender);
    Class<? extends Appender> getAppenderType(String type);
    boolean containsAppender(String type);
}
