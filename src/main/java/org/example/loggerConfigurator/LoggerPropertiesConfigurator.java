package org.example.loggerConfigurator;

import org.example.Logger;
import org.example.appender.Appender;
import org.example.appender.AppenderRegister;
import org.example.appender.ConsoleAppender;
import org.example.propertiesParser.DeserializerRegister;
import org.example.propertiesParser.deserializer.PropertiesDeserializer;
import org.example.propertiesParser.deserializer.PropsDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class LoggerPropertiesConfigurator extends AbstractConfigurator {

    private static final String PREFIX = "log.appender.";
    private static final String NAMES = "log.appenders";
    private final String pathToSettings;
    private final DeserializerRegister deserializerRegister;

    public LoggerPropertiesConfigurator(String pathToSettings, AppenderRegister appenderRegister) {
        super(appenderRegister);
        this.pathToSettings = pathToSettings;
        this.deserializerRegister = new DeserializerRegister();
    }

    @Override
    public Logger createLogger() {
        Properties properties = readProperties();
        Logger logger = new Logger();
        String[] appenders = properties.getProperty(NAMES).split("\\s*,\\s*");
        for (String appenderName : appenders) {
            String namedPrefix = "%s%s".formatted(PREFIX, appenderName);
            String type = properties.getProperty("%s.type".formatted(namedPrefix));
            Class<? extends Appender> appenderClass = getAppenderRegister().getAppenderType(type);
            if (appenderClass == null) {
                System.err.println("Unknown appender type: " + type);
                continue;
            }
            try {
                Appender appender = appenderClass.getDeclaredConstructor().newInstance();
                Field[] baseFields = appenderClass.getSuperclass().getDeclaredFields();
                configureAppender(baseFields, appender, properties, namedPrefix);
                Field[] individualFields = appenderClass.getDeclaredFields();
                configureAppender(individualFields, appender, properties, namedPrefix);
                logger.addAppender(appender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Appender> appenderList = logger.getAppenders();
        if (appenderList.isEmpty()) {
            appenderList.add(new ConsoleAppender());
        }
        return logger;
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get(pathToSettings))) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private void configureAppender(
            Field[] fields,
            Appender appender,
            Properties properties,
            String namedPrefix
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Field field : fields) {

            field.setAccessible(true);
            String key = "%s.%s".formatted(namedPrefix, field.getName());
            String value = properties.getProperty(key);
            if (value != null && !value.isBlank()) {
                PropsDeserializer propsDeserializerAnnotation = field.getAnnotation(PropsDeserializer.class);
                Class<? extends PropertiesDeserializer<?>> deserializerClass = propsDeserializerAnnotation == null ?
                        deserializerRegister.getDeserializer(field.getType())
                        : propsDeserializerAnnotation.using();
                if (deserializerClass == null) {
                    System.err.println("Unknown deserializer type: " + field.getType());
                    continue;
                }
                PropertiesDeserializer<?> deserializer = deserializerClass.getDeclaredConstructor().newInstance();
                Object convertedValue = deserializer.deserialize(value);
                field.set(appender, convertedValue);
            }
        }
    }
}
