package org.example.loggerConfigurator;

import org.example.appender.AppenderRegister;

public abstract class AbstractConfigurator implements LoggerConfigurator {
    private final AppenderRegister appenderRegister;

    public AbstractConfigurator(AppenderRegister appenderRegister) {
        this.appenderRegister = appenderRegister;
    }

    public AppenderRegister getAppenderRegister() {
        return appenderRegister;
    }
}
