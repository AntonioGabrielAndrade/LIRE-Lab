package br.com.antoniogabriel.lirelab.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public List<String> getAllImagesPaths(String imagesDir, boolean scanSubdirectories) throws IOException {
        return net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(imagesDir), scanSubdirectories);
    }
}
