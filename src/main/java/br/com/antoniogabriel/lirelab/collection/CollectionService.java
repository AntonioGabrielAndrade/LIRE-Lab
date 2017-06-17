package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.inject.Inject;
import java.util.List;

public class CollectionService {

    private PathResolver resolver;

    @Inject
    public CollectionService(PathResolver resolver) {
        this.resolver = resolver;
    }


    public CreateCollectionTask getCreateTask(String collectionName,
                                              String imagesPath,
                                              List<Feature> collectionFeatures) {

        return new CreateCollectionTaskFactory()
                            .createTask(
                                    collectionName,
                                    collectionFeatures,
                                    imagesPath,
                                    resolver.getCollectionPath(collectionName),
                                    resolver.getIndexDirectoryPath(collectionName),
                                    resolver.getThumbnailsDirectoryPath(collectionName)
                            );

    }
}
