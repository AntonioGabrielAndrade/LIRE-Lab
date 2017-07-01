package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback, XMLCreatorCallback {
    private final IndexCreator indexCreator;
    private final ThumbnailsCreator thumbnailsCreator;
    private final XMLCreator xmlCreator;

    public CreateCollectionTask(IndexCreator indexCreator,
                                ThumbnailsCreator thumbnailsCreator,
                                XMLCreator xmlCreator) {

        this.indexCreator = indexCreator;
        this.thumbnailsCreator = thumbnailsCreator;
        this.xmlCreator = xmlCreator;

        setItselfAsCallback();
    }

    private void setItselfAsCallback() {
        this.indexCreator.setCallback(this);
        this.thumbnailsCreator.setCallback(this);
        this.xmlCreator.setCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        indexCreator.create();
        thumbnailsCreator.create();
        xmlCreator.create();
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
