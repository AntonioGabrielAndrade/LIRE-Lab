package br.com.antoniogabriel.lirelab.util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }
}
