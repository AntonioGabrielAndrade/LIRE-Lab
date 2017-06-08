package br.com.antoniogabriel.lirelab.collection;

import java.io.IOException;
import java.util.List;

public class ThumbnailsCreator {

    private ThumbnailBuilder builder;
    private ThumbnailsCreatorCallback callback;
    private String thumbnailsDir;
    private String imagesDir;

    public ThumbnailsCreator(ThumbnailBuilder builder) {
        this.builder = builder;
    }

    public void setCallback(ThumbnailsCreatorCallback callback) {
        this.callback = callback;
    }

    public void create() throws IOException {
        builder.createDirectory(thumbnailsDir);
        List<String> images = builder.getAllImagePaths(imagesDir);

        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i);
            callback.beforeCreateThumbnail(i+1, images.size(), image);
            builder.createThumbnail(image, thumbnailsDir);
            callback.afterCreateThumbnail(i+1, images.size(), image);
        }

        callback.afterCreateAllThumbnails(images.size());
    }

    public void setThumbnailsDir(String thumbnailsDir) {
        this.thumbnailsDir = thumbnailsDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }
}
