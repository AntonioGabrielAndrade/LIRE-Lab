package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.QueryRunnerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Singleton
public class CollectionService {

    private PathResolver resolver;
    private CollectionRepository collectionRepository;
    private CollectionsMonitor collectionsMonitor;
    private CreateCollectionRunnerFactory createCollectionRunnerFactory;
    private QueryRunnerFactory queryRunnerFactory;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor,
                             CreateCollectionRunnerFactory createCollectionRunnerFactory,
                             QueryRunnerFactory queryRunnerFactory) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;
        this.createCollectionRunnerFactory = createCollectionRunnerFactory;
        this.queryRunnerFactory = queryRunnerFactory;

        startMonitoringCollectionsInFileSystem();
    }

    private void startMonitoringCollectionsInFileSystem() {
        try {
            collectionsMonitor.startMonitoringCollectionsModificationsInFileSystem();
        } catch (IOException e) {
            throw new LireLabException("Error monitoring collections", e);
        }
    }

    public CreateCollectionRunner getCreateCollectionRunner(CreateCollectionInfo createInfo) {
        CreateCollectionRunner runner = createCollectionRunnerFactory.getCreateRunner(createInfo);
        runner.setOnFinish(() -> collectionsMonitor.executeListeners());
        return runner;
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

    public Set<String> getCollectionNames() {
        return collectionRepository.getCollectionNames();
    }
}
