package org.example.stub;

import org.example.appender.AbstractAppender;
import org.example.propertiesParser.deserializer.PropsDeserializer;

import java.net.URL;

public class AppenderUrlStub extends AbstractAppender {

    @PropsDeserializer(using = UrlDeserializerStub.class)
    private URL url;
    @PropsDeserializer(using = IntegerDeserializerStub.class)
    private int port;
    @Override
    public void append(String message) {
        // stub
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
