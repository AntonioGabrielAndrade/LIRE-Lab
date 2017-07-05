package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"name", "imagesDirectory", "features"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection {

    @XmlElement
    private String name = "";

    @XmlElement
    private String imagesDirectory = "";

    @XmlElementWrapper(name="features")
    @XmlElement(name="feature")
    private List<Feature> features = new ArrayList<>();

    @XmlTransient
    private List<String> imagePaths = new ArrayList<>();

    @XmlTransient
    private List<String> thumbnailPaths = new ArrayList<>();

    @XmlTransient
    private List<Image> images = new ArrayList<>();

    public Collection() {}

    public Collection(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setImagesDirectory(String imagesDirectory) {
        this.imagesDirectory = imagesDirectory;
    }

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public List<String> getThumbnailPaths() {
        return thumbnailPaths;
    }

    public void setThumbnailPaths(List<String> thumbnailPaths) {
        this.thumbnailPaths = thumbnailPaths;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int totalImages() {
        return images.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collection that = (Collection) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (features != null ? !features.equals(that.features) : that.features != null) return false;
        return imagesDirectory != null ? imagesDirectory.equals(that.imagesDirectory) : that.imagesDirectory == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (features != null ? features.hashCode() : 0);
        result = 31 * result + (imagesDirectory != null ? imagesDirectory.hashCode() : 0);
        return result;
    }

}
