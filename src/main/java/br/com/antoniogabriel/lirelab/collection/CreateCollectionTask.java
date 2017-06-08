package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexBuilderCallback;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexBuilderCallback {
    private IndexCreator creator;

    public CreateCollectionTask(IndexCreator creator) {
        this.creator = creator;
        this.creator.setCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        creator.create();
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
