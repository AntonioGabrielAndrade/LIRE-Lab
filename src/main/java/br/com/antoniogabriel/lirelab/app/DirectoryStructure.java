package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryStructure {
    private PathResolver resolver;

    @Inject
    public DirectoryStructure(PathResolver resolver) {
        this.resolver = resolver;
    }

    public void init() throws IOException {
        createCollectionsDirectory();
    }

    public void createCollectionsDirectory() throws IOException {
        Files.createDirectories(Paths.get(resolver.getCollectionsPath()));
    }
}
