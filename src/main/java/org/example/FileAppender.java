package org.example;

public class FileAppender implements Appender {
    private String filePath;

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(String message) {
        // Реализация записи в файл
    }
}
