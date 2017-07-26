package br.com.antoniogabriel.lirelab.collection;

import java.io.IOException;
import java.util.List;

public class ThumbnailsCreator {

    private ThumbnailsCreatorCallback callback = new DumbThumbnailsCreatorCallback();
    private ThumbnailBuilder builder;
    private String thumbnailsDir;
    private List<String> images;
    private int thumbnailHeight;

    public ThumbnailsCreator(ThumbnailBuilder builder,
                             String thumbnailsDir,
                             List<String> paths,
                             int thumbnailHeight) {

        this.builder = builder;
        this.thumbnailsDir = thumbnailsDir;
        this.images = paths;
        this.thumbnailHeight = thumbnailHeight;
    }

    public void create() throws IOException {
        builder.createDirectory(thumbnailsDir);

        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i);
            callback.beforeCreateThumbnail(i+1, images.size(), image);
            builder.createThumbnail(image, thumbnailsDir, thumbnailHeight);
            callback.afterCreateThumbnail(i+1, images.size(), image);
        }

        callback.afterCreateAllThumbnails(images.size());
    }

    public void setCallback(ThumbnailsCreatorCallback callback) {
        this.callback = callback;
    }

    private class DumbThumbnailsCreatorCallback implements ThumbnailsCreatorCallback {
        @Override
        public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {}

        @Override
        public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {}

        @Override
        public void afterCreateAllThumbnails(int totalImages) {}
    }
}
