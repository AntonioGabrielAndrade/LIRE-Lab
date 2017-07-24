package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import java.util.List;

public class CreateCollectionInfo {
    private final String collectionName;
    private final String imagesDirectory;
    private final List<Feature> features;
    private final boolean scanSubdirectories;
    private final boolean useParallelIndexer;
    private final int numberOfThreads;

    public CreateCollectionInfo(String collectionName,
                                String imagesDirectory,
                                List<Feature> features,
                                boolean scanSubdirectories,
                                boolean useParallelIndexer,
                                int numberOfThreads) {

        this.collectionName = collectionName;
        this.imagesDirectory = imagesDirectory;
        this.features = features;
        this.scanSubdirectories = scanSubdirectories;
        this.useParallelIndexer = useParallelIndexer;
        this.numberOfThreads = numberOfThreads;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public boolean isScanSubdirectories() {
        return scanSubdirectories;
    }

    public boolean isUseParallelIndexer() {
        return useParallelIndexer;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }
}
