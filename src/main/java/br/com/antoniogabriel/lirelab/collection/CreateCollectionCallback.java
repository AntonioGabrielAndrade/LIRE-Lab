package br.com.antoniogabriel.lirelab.collection;

public interface CreateCollectionCallback {
    void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterIndexAllImages(int totalImages);
}
