package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class CollectionService {

    private PathResolver resolver;
    private CollectionRepository collectionRepository;
    private CollectionsMonitor collectionsMonitor;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;

        startMonitoringCollectionsDeleteAndUpdate();
    }

    private void startMonitoringCollectionsDeleteAndUpdate() {
        try {
            this.collectionsMonitor.startMonitoringCollectionsDeleteAndUpdate();
        } catch (IOException e) {
            throw new LireLabException("Error monitoring collections", e);
        }
    }


    public CreateCollectionTask getTaskToCreateCollection(String collectionName,
                                                          String imagesPath,
                                                          List<Feature> collectionFeatures) {

        CreateCollectionTask task =
                new CreateCollectionTaskFactory()
                                .createTask(
                                        collectionName,
                                        collectionFeatures,
                                        imagesPath,
                                        resolver.getCollectionPath(collectionName),
                                        resolver.getIndexDirectoryPath(collectionName),
                                        resolver.getThumbnailsDirectoryPath(collectionName)
                                );

        collectionsMonitor.bindListenersTo(task);
        return task;

    }

    public List<Collection> getCollections() {
        return collectionRepository.getCollections();
    }

    public void addCollectionsChangeListener(Runnable callback) {
        collectionsMonitor.addListener(callback);
    }
}
