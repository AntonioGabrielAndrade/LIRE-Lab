package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexBuilder;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;

import java.util.List;

public class CreateCollectionTaskFactory {

    public CreateCollectionTask createTask(String collectionName,
                                           List<Feature> collectionFeatures,
                                           String collectionDirectory,
                                           String imagesDirectory) {

        String indexDirectory = collectionDirectory + "/index";
        String thumbnailsDirectory = collectionDirectory + "/thumbnails";

        IndexBuilder indexBuilder = new IndexBuilder();
        IndexCreator indexCreator = new IndexCreator(indexBuilder,
                                                    imagesDirectory,
                                                    indexDirectory,
                                                    collectionFeatures);

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                                                                    thumbnailsDirectory,
                                                                    imagesDirectory);

        return new CreateCollectionTask(indexCreator, thumbnailsCreator);

    }
}
