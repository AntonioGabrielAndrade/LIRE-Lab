package br.com.antoniogabriel.lirelab.lire;

class DumbIndexCreatorCallback implements IndexCreatorCallback {
    @Override
    public void beforeIndexImages() {}

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

    @Override
    public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

    @Override
    public void afterIndexAllImages(int totalImages) {}
}
