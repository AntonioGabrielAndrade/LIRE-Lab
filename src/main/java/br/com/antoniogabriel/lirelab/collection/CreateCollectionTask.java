package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback, XMLCreatorCallback {

    private CreateCollectionRunnable runnable;

    public CreateCollectionTask(CreateCollectionRunnable runnable) {
        this.runnable = runnable;

        runnable.setIndexCreatorCallback(this);
        runnable.setThumbnailsCreatorCallback(this);
        runnable.setXmlCreatorCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        runnable.run();
        return null;
    }

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
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
    public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {
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
        updateMessage("Collection Created");
        updateProgress(1,1);
    }
}
