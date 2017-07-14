package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.QueryRunnerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class CollectionService {

    private PathResolver resolver;
    private CollectionRepository collectionRepository;
    private CollectionsMonitor collectionsMonitor;
    private CreateCollectionTaskFactory createCollectionTaskFactory;
    private QueryRunnerFactory queryRunnerFactory;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor,
                             CreateCollectionTaskFactory createCollectionTaskFactory, QueryRunnerFactory queryRunnerFactory) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;
        this.createCollectionTaskFactory = createCollectionTaskFactory;
        this.queryRunnerFactory = queryRunnerFactory;

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
                                                          List<Feature> collectionFeatures,
                                                          boolean scanSubdirectories,
                                                          boolean useParallelIndexer,
                                                          int numberOfThreads) {

        CreateCollectionTask task =
                createCollectionTaskFactory.createTask(
                                                collectionName,
                                                collectionFeatures,
                                                imagesPath,
                                                scanSubdirectories,
                                                useParallelIndexer,
                                                numberOfThreads);

        collectionsMonitor.bindListenersTo(task);
        return task;

    }

    public List<Collection> getCollections() {
        return collectionRepository.getCollections();
    }

    public void addCollectionsChangeListener(Runnable callback) {
        collectionsMonitor.addListener(callback);
    }

    public List<Image> runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {
        return queryRunnerFactory.createQueryRunner(resolver).runQuery(collection, feature, queryImage);
    }

    public void deleteCollection(Collection collection) {
        collectionRepository.deleteCollection(collection.getName());
    }
}
