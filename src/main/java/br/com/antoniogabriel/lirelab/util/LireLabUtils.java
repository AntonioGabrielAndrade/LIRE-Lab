package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.collection.PathResolver;

import javax.inject.Inject;
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

    public Path getCollectionsPath() {
        return Paths.get(resolver.getCollectionsPath());
    }

    public Path getCollectionPath(String collectionName) {
        return Paths.get(resolver.getCollectionPath(collectionName));
    }
}
