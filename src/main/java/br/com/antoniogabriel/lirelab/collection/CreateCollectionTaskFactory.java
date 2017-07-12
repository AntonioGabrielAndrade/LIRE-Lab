package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.LIRE;

import java.io.File;
import java.util.List;

public class CreateCollectionTaskFactory {

    public CreateCollectionTask createTask(String collectionName,
                                           List<Feature> collectionFeatures,
                                           String imagesPath,
                                           String collectionPath,
                                           String indexPath,
                                           String thumbnailsPath) {

        return new CreateCollectionTask(createTaskAsRunnable(collectionName, collectionFeatures, imagesPath, collectionPath, indexPath, thumbnailsPath));

    }

    public CreateCollectionRunnable createTaskAsRunnable(String collectionName,
                                           List<Feature> collectionFeatures,
                                           String imagesPath,
                                           String collectionPath,
                                           String indexPath,
                                           String thumbnailsPath) {

        LIRE lire = new LIRE();
        IndexCreator indexCreator = new IndexCreator(lire,
                imagesPath,
                indexPath,
                collectionFeatures);

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                thumbnailsPath,
                imagesPath);

        CollectionXMLDAO xmlDAO = new CollectionXMLDAO(new File(collectionPath));
        XMLCreator xmlCreator = new XMLCreator(collectionName,
                imagesPath,
                collectionFeatures,
                xmlDAO);

        return new CreateCollectionRunnable(indexCreator, thumbnailsCreator, xmlCreator);
    }
}
