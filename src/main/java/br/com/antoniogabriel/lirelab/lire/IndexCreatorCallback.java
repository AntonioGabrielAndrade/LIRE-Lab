package br.com.antoniogabriel.lirelab.lire;

public interface IndexCreatorCallback {
    void beforeIndexImages();

    void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath);

    void afterIndexAllImages(int totalImages);

    void updatePercentage(double percentageDone);
}
