package br.com.antoniogabriel.lirelab.collection;

public class PathResolver {
    public static final String HOME_DIRECTORY_PATH = System.getProperty("user.home");
    public static final String LIRELAB_WORK_DIRECTORY = "/lirelab";
    public static final String COLLECTIONS_DIRECTORY = "/collections";
    public static final String COLLECTIONS_PATH = HOME_DIRECTORY_PATH
                                                + LIRELAB_WORK_DIRECTORY
                                                + COLLECTIONS_DIRECTORY;

    public String getCollectionPath(String collectionName) {
        return COLLECTIONS_PATH + "/" + collectionName;
    }
}
