package br.com.antoniogabriel.lirelab.acceptance;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.collection.CreateCollectionController.COLLECTIONS_PATH;
import static org.junit.Assert.assertTrue;

public class CollectionHelper {

    public void checkCollectionExists(String collection) {
        assertTrue(directoryExistsFor(collection));
        assertTrue(directoryExistsFor(indexFolderOf(collection)));
        assertTrue(directoryExistsFor(thumbnailsFolderOf(collection)));
        assertTrue(fileExistsFor(xmlFileOf(collection)));
    }

    public void deleteCollection(String collection) throws IOException {
        FileUtils.deleteDirectory(pathTo(collection).toFile());
    }

    private boolean fileExistsFor(String pathName) {
        Path path = pathTo(pathName);
        return Files.exists(path) && Files.isRegularFile(path);
    }

    private boolean directoryExistsFor(String pathName) {
        Path path = pathTo(pathName);
        return Files.exists(path) && Files.isDirectory(path);
    }

    private Path pathTo(String collection) {
        return Paths.get(COLLECTIONS_PATH + "/" + collection);
    }

    private String thumbnailsFolderOf(String collection) {
        return collection + "/thumbnails";
    }

    private String indexFolderOf(String collection) {
        return collection + "/index";
    }

    private String xmlFileOf(String collection) {
        return collection + "/collection.xml";
    }
}
