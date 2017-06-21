package br.com.antoniogabriel.lirelab.collection;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PathResolver {
    public static final String LIRELAB_WORK_DIRECTORY = "/lirelab";
    public static final String COLLECTIONS_DIRECTORY = "/collections";
    public static final String INDEX_DIRECTORY = "/index";
    public static final String THUMBNAILS_DIRECTORY = "/thumbnails";
    public static final String COLLECTION_XML = "collection.xml";

    public static final String HOME_DIRECTORY_PATH = System.getProperty("user.home");
    private String applicationBasePath = "";

    public PathResolver() {
        this.applicationBasePath = HOME_DIRECTORY_PATH;
    }

    public PathResolver(String applicationBasePath) {
        this.applicationBasePath = new File(applicationBasePath).getAbsolutePath();
    }

    public String getCollectionPath(String collectionName) {
        return getCollectionsPath() + "/" + collectionName;
    }

    @NotNull
    public String getCollectionsPath() {
        return getApplicationBasePath() + LIRELAB_WORK_DIRECTORY + COLLECTIONS_DIRECTORY;
    }

    public String getApplicationBasePath() {
        return applicationBasePath;
    }

    public String getWorkDirectoryPath() {
        return getApplicationBasePath() + LIRELAB_WORK_DIRECTORY;
    }

    public String getCollectionXMLPath(String collectionName) {
        return getCollectionsPath() + "/" + collectionName + "/" + COLLECTION_XML;
    }

    public String getIndexDirectoryPath(String collectionName) {
        return getCollectionPath(collectionName) + INDEX_DIRECTORY;
    }

    public String getThumbnailsDirectoryPath(String collectionName) {
        return getCollectionPath(collectionName) + THUMBNAILS_DIRECTORY;
    }
}
