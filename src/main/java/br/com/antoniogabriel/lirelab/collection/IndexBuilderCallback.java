package br.com.antoniogabriel.lirelab.collection;

public interface IndexBuilderCallback {
    void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterIndexAllImages(int totalImages);
}
