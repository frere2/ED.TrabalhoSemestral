package br.edu.fateczl.ed.Infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Paths;
import java.nio.file.Path;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getFullPath(String fileName) {
        String basePath = getProperty("repository.base.path");
        if (basePath != null) {
            Path fullPath = Paths.get(basePath, fileName);
            return fullPath.toAbsolutePath().toString();
        }
        return null;
    }
}