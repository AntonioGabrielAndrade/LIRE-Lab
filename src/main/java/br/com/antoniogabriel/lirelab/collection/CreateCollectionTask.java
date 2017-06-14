package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback {
    private final IndexCreator indexCreator;
    private final ThumbnailsCreator thumbnailsCreator;

    public CreateCollectionTask(IndexCreator indexCreator, ThumbnailsCreator thumbnailsCreator) {
        this.indexCreator = indexCreator;
        this.thumbnailsCreator = thumbnailsCreator;

        setItselfAsCallback();
    }

    private void setItselfAsCallback() {
        this.indexCreator.setCallback(this);
        this.thumbnailsCreator.setCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        indexCreator.create();
        thumbnailsCreator.create();
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

    @Override
    public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateMessage("Creating thumbnail for  " +
                Paths.get(imagePath).getFileName().toString());
    }

    @Override
    public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterCreateAllThumbnails(int totalImages) {
        updateMessage("Done!");
    }
}
