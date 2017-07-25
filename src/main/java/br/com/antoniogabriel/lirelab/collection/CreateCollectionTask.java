package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback, XMLCreatorCallback {

    private CreateCollectionRunner runner;

    public CreateCollectionTask(CreateCollectionRunner runner) {
        this.runner = runner;

        updateTitle("Create Collection...");
        runner.setIndexCreatorCallback(this);
        runner.setThumbnailsCreatorCallback(this);
        runner.setXmlCreatorCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        runner.run();
        return null;
    }

    @Override
    public void beforeIndexImages() {
        updateTitle("Step 1: Create index");
        updateMessage("Indexing all images...");
    }

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        updateTitle("Step 1: Create index");
        updateMessage("Indexing " + Paths.get(imageFilePath).getFileName().toString());
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
    public void updatePercentage(double percentageDone) {
        updateProgress(percentageDone, 1.0);
    }

    @Override
    public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateTitle("Step 2: Create thumbnails");
        updateMessage("Creating thumbnail for  " + Paths.get(imagePath).getFileName().toString());
    }

    @Override
    public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterCreateAllThumbnails(int totalImages) {
        updateMessage("Done!");
    }

    @Override
    public void beforeCreateXML() {
        updateProgress(-1,1);
        updateMessage("Creating collection.xml...");
    }

    @Override
    public void afterCreateXML() {
        updateTitle("Completed");
        updateMessage("Collection Created!");
        updateProgress(1,1);
    }
}
