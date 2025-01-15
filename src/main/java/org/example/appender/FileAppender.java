package org.example.appender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileAppender extends AbstractAppender {

    private String filePath;

    public FileAppender() {}

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(String message) {
        try {
            Path path = Path.of(filePath);
            createParentDirectory(path);
            Files.writeString(path, message + System.lineSeparator(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createParentDirectory(Path path) {
        Path parentDir = path.getParent();
        if (parentDir != null) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
