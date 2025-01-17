package org.example.jsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.appender.Appender;
import org.example.appenderConfigurator.register.AppendersRegister;
import org.example.appenderConfigurator.register.Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppenderListDeserializer extends JsonDeserializer<List<Appender>> {

    private final Register register;

    public AppenderListDeserializer() {
        register = new AppendersRegister();
    }

    @Override
    public List<Appender> deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        List<Appender> appenders = new ArrayList<>();
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode appendersNode = mapper.readTree(jsonParser);
        for (JsonNode node : appendersNode) {
            if (node.isObject()) {
                JsonNode typeNode = node.get("type");
                if (typeNode != null && typeNode.isTextual()) {
                    String type = typeNode.asText();
                    Class<? extends Appender> appenderClass = register.getAppenderType(type);

                    if (appenderClass != null) {
                        Appender appender = mapper.treeToValue(node, appenderClass);
                        appenders.add(appender);
                    }
                }
            }
        }
        return appenders;
    }
}