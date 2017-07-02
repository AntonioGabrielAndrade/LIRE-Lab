package br.com.antoniogabriel.lirelab.collection;

import java.nio.file.Paths;

public class Image {

    private final String imagePath;
    private final String thumbnailPath;

    public Image(String imagePath, String thumbnailPath) {
        this.imagePath = imagePath;
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getImageName() {
        return Paths.get(imagePath).getFileName().toString().replace(".jpg", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (imagePath != null ? !imagePath.equals(image.imagePath) : image.imagePath != null) return false;
        return thumbnailPath != null ? thumbnailPath.equals(image.thumbnailPath) : image.thumbnailPath == null;
    }

    @Override
    public int hashCode() {
        int result = imagePath != null ? imagePath.hashCode() : 0;
        result = 31 * result + (thumbnailPath != null ? thumbnailPath.hashCode() : 0);
        return result;
    }

}
