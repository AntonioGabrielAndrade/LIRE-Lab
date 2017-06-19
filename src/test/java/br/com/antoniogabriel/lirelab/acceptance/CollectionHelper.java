package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class CollectionHelper {

    private PathResolver resolver = new PathResolver();

    public void checkCollectionExists(String collection) {
        assertTrue(directoryExists(resolver.getCollectionPath(collection)));
        assertTrue(directoryExists(resolver.getIndexDirectoryPath(collection)));
        assertTrue(directoryExists(resolver.getThumbnailsDirectoryPath(collection)));
        assertTrue(fileExists(resolver.getCollectionXMLPath(collection)));
    }

    private boolean directoryExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isDirectory(Paths.get(path));
    }

    private boolean fileExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isRegularFile(Paths.get(path));
    }

    public void deleteCollection(String collectionName) throws IOException {
        FileUtils.deleteDirectory(new File(resolver.getCollectionPath(collectionName)));
    }
}
