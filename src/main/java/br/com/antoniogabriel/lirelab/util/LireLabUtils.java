package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.collection.PathResolver;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LireLabUtils {

    private PathResolver resolver;
    private FileUtils fileUtils;

    @Inject
    public LireLabUtils(PathResolver resolver, FileUtils fileUtils) {
        this.resolver = resolver;
        this.fileUtils = fileUtils;
    }

    public boolean collectionsDirectoryExist() {
        return fileUtils.fileExists(resolver.getCollectionsPath());
    }

    public boolean isCollection(Path dir) {
        return fileUtils.fileExists(dir.toAbsolutePath().toString() + "/" + PathResolver.COLLECTION_XML);
    }

    public Path getCollectionsPath() {
        return Paths.get(resolver.getCollectionsPath());
    }

    public Path getCollectionPath(String collectionName) {
        return Paths.get(resolver.getCollectionPath(collectionName));
    }

    public Path getCollectionXMLPath(String collectionName) {
        return Paths.get(resolver.getCollectionXMLPath(collectionName));
    }

    public void deleteCollection(String collectionName) throws IOException {
        Path dirPath = getCollectionPath(collectionName);
        Path xmlPath = getCollectionXMLPath(collectionName);

        Files.delete(xmlPath);
        org.apache.commons.io.FileUtils.deleteDirectory(dirPath.toFile());
    }
}
