package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.LIRE;
import br.com.antoniogabriel.lirelab.util.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateCollectionTaskFactory {

    private PathResolver resolver;
    private FileUtils fileUtils;

    @Inject
    public CreateCollectionTaskFactory(PathResolver resolver, FileUtils fileUtils) {
        this.resolver = resolver;
        this.fileUtils = fileUtils;
    }

    public CreateCollectionTask createTask(String collectionName,
                                           List<Feature> collectionFeatures,
                                           String imagesPath,
                                           boolean scanSubdirectories) {

        return new CreateCollectionTask(createTaskAsRunnable(collectionName, collectionFeatures, imagesPath, scanSubdirectories));

    }

    public CreateCollectionRunnable createTaskAsRunnable(String collectionName,
                                                         List<Feature> collectionFeatures,
                                                         String imagesPath,
                                                         boolean scanSubdirectories) {


        String collectionPath = resolver.getCollectionPath(collectionName);
        String indexPath = resolver.getIndexDirectoryPath(collectionName);
        String thumbnailsPath = resolver.getThumbnailsDirectoryPath(collectionName);


        LIRE lire = new LIRE();
        List<String> paths;

        try {
            paths = fileUtils.getAllImagesPaths(imagesPath, scanSubdirectories);
        } catch (IOException e) {
            throw new LireLabException("Could not read paths", e);
        }

        IndexCreator indexCreator = new IndexCreator(lire,
                indexPath,
                collectionFeatures,
                paths);

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                thumbnailsPath,
                paths);

        CollectionXMLDAO xmlDAO = new CollectionXMLDAO(new File(collectionPath));
        XMLCreator xmlCreator = new XMLCreator(collectionName,
                imagesPath,
                collectionFeatures,
                xmlDAO);

        return new CreateCollectionRunnable(indexCreator, thumbnailsCreator, xmlCreator);
    }
}
