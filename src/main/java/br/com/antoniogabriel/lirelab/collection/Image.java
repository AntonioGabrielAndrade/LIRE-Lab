package br.com.antoniogabriel.lirelab.collection;

import java.nio.file.Paths;

public class Image {

    private final String imagePath;
    private final String thumbnailPath;

    private double score = -1;
    private int position = -1;
    private int docId = -1;

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

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getDocId() {
        return docId;
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

    @Override
    public String toString() {
        return getImageName();
    }

}
