package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionXMLDAO;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class CollectionHelper {

    private PathResolver resolver;

    @Inject
    public CollectionHelper(PathResolver resolver) {
        this.resolver = resolver;
    }

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

    public void createStubCollection(Collection collection) throws IOException, JAXBException {
        Files.createDirectories(Paths.get(resolver.getCollectionPath(collection.getName())));
        Files.createDirectories(Paths.get(resolver.getIndexDirectoryPath(collection.getName())));
        Files.createDirectories(Paths.get(resolver.getThumbnailsDirectoryPath(collection.getName())));

        CollectionXMLDAO collectionDao = new CollectionXMLDAO(new File(resolver.getCollectionPath(collection.getName())));
        collectionDao.create(collection);
    }
}
