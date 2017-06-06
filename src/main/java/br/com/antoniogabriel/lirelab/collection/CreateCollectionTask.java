package br.com.antoniogabriel.lirelab.collection;

import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements CreateCollectionCallback {
    private CollectionCreator creator;

    public CreateCollectionTask(CollectionCreator creator) {
        this.creator = creator;
        this.creator.setCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        creator.createIndex();
        creator.addImagesToIndex();
        return null;
    }

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        updateMessage("Indexing " +
                Paths.get(imageFilePath).getFileName().toString());
    }

    @Override
    public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterIndexAllImages(int totalImages) {
        updateMessage("Indexing complete!");
    }
}
