package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionXMLDAO;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class CollectionHelper {

    private PathResolver resolver;

    @Inject
    public CollectionHelper(PathResolver resolver) {
        this.resolver = resolver;
    }

    public void checkCollectionExists(String collection) {
        assertTrue(rootFolderExist(collection));
        assertTrue(indexFolderExist(collection));
        assertTrue(thumbnailsFolderExist(collection));
        assertTrue(xmlFileExist(collection));
    }

    public void deleteCollection(Collection collection) throws IOException {
        deleteCollection(collection.getName());
    }

    public void deleteCollection(String collectionName) throws IOException {
        FileUtils.deleteDirectory(new File(resolver.getCollectionPath(collectionName)));
    }

    public void createStubCollection(Collection collection) throws IOException, JAXBException {
        createRootFolder(collection);
        createIndexFolder(collection);
        createThumbnailsFolder(collection);
        createXMLFile(collection);
    }

    private boolean xmlFileExist(String collection) {
        return fileExists(resolver.getCollectionXMLPath(collection));
    }

    private boolean thumbnailsFolderExist(String collection) {
        return directoryExists(resolver.getThumbnailsDirectoryPath(collection));
    }

    private boolean indexFolderExist(String collection) {
        return directoryExists(resolver.getIndexDirectoryPath(collection));
    }

    private boolean rootFolderExist(String collection) {
        return directoryExists(resolver.getCollectionPath(collection));
    }

    private boolean directoryExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isDirectory(Paths.get(path));
    }

    private boolean fileExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isRegularFile(Paths.get(path));
    }

    @Nullable
    private Path createRootFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getCollectionPath(collection.getName())));
    }

    @Nullable
    private Path createThumbnailsFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getThumbnailsDirectoryPath(collection.getName())));
    }

    @Nullable
    private Path createIndexFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getIndexDirectoryPath(collection.getName())));
    }

    private void createXMLFile(Collection collection) throws JAXBException {
        CollectionXMLDAO collectionDao = new CollectionXMLDAO(new File(resolver.getCollectionPath(collection.getName())));
        collectionDao.create(collection);
    }
}
