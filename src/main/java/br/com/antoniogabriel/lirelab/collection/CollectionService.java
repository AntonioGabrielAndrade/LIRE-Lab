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
    private CreateCollectionRunnableFactory createCollectionRunnableFactory;
    private QueryRunnerFactory queryRunnerFactory;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor,
                             CreateCollectionRunnableFactory createCollectionRunnableFactory, QueryRunnerFactory queryRunnerFactory) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;
        this.createCollectionRunnableFactory = createCollectionRunnableFactory;
        this.queryRunnerFactory = queryRunnerFactory;

        startMonitoringCollectionsDeleteAndUpdate();
    }

    private void startMonitoringCollectionsDeleteAndUpdate() {
        try {
            collectionsMonitor.startMonitoringCollectionsDeleteAndUpdate();
        } catch (IOException e) {
            throw new LireLabException("Error monitoring collections", e);
        }
    }

    public CreateCollectionRunnable getCreateCollectionRunnable(CreateCollectionInfo createInfo) {
        CreateCollectionRunnable runnable = createCollectionRunnableFactory.getCreationRunnable(createInfo);
        runnable.setOnFinish(() -> collectionsMonitor.executeListeners());
        return runnable;
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
        collectionsMonitor.executeListeners();
    }
}
