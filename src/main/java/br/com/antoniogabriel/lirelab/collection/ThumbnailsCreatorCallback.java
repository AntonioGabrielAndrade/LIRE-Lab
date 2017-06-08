package br.com.antoniogabriel.lirelab.collection;

public interface ThumbnailsCreatorCallback {

    void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath);

    void afterCreateThumbnail(int currentImage, int totalImages, String imagePath);

    void afterCreateAllThumbnails(int totalImages);
}