package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.inject.Inject;
import java.util.List;

public class CollectionService {

    private PathResolver resolver;

    private CollectionRepository collectionRepository;

    @Inject
    public CollectionService(PathResolver resolver, CollectionRepository collectionRepository) {
        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
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

    public List<Collection> getCollections() {
        return collectionRepository.getCollections();
    }
}
