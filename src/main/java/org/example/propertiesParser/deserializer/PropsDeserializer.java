package org.example.propertiesParser.deserializer;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropsDeserializer {
    Class<? extends PropertiesDeserializer<?>> using();
}
