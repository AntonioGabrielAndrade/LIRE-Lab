package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexBuilder;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;

import java.io.File;
import java.util.List;

public class CreateCollectionTaskFactory {

    public CreateCollectionTask createTask(String collectionName,
                                           List<Feature> collectionFeatures,
                                           String collectionDirectory,
                                           String imagesDirectory,
                                           String indexDirectory,
                                           String thumbnailsDirectory) {

        IndexBuilder indexBuilder = new IndexBuilder();
        IndexCreator indexCreator = new IndexCreator(indexBuilder,
                                                    imagesDirectory,
                                                    indexDirectory,
                                                    collectionFeatures);

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                                                                    thumbnailsDirectory,
                                                                    imagesDirectory);

        CollectionXMLDAO xmlDAO = new CollectionXMLDAO(new File(collectionDirectory));
        XMLCreator xmlCreator = new XMLCreator(collectionName,
                                                imagesDirectory,
                                                collectionFeatures,
                                                xmlDAO);

        return new CreateCollectionTask(indexCreator, thumbnailsCreator, xmlCreator);

    }
}
