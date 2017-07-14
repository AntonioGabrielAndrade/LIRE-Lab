package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;

import java.io.IOException;
import java.util.List;

public class ParallelIndexCreator implements IndexCreator {

    private IndexCreatorCallback callback = new DumbIndexCreatorCallback();
    private List<Feature> features;
    private String imagesDir;
    private int numberOfThreads;
    private String indexDir;
    private LIRE lire;

    public ParallelIndexCreator(LIRE lire,
                                String indexDir,
                                List<Feature> features,
                                String imagesDir,
                                int numberOfThreads) {

        this.lire = lire;
        this.indexDir = indexDir;
        this.features = features;
        this.imagesDir = imagesDir;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void create() throws IOException {
        ParallelIndexer indexer = lire.createParallelIndexer(numberOfThreads, indexDir, imagesDir);
        callback.beforeIndexImages();
        addFeaturesToIndexer(features, indexer);

        new Thread(() -> indexer.run()).start();

        while(!indexer.hasEnded()) {
            try {
                Thread.sleep(2000);
                callback.updatePercentage(indexer.getPercentageDone());
            } catch (InterruptedException e) {
                continue;
            }
        }

        callback.updatePercentage(1.0);
    }

    private void addFeaturesToIndexer(List<Feature> features, ParallelIndexer indexer) {
        for (Feature feature : features) {
            indexer.addExtractor(feature.getLireClass());
        }
    }

    @Override
    public void setCallback(IndexCreatorCallback callback) {
        this.callback = callback;
    }
}
