package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.Feature;

import java.util.ArrayList;

public class CollectionCreatorBuilder {
    private IndexCreator creator;
    private ArrayList<Feature> features;
    private String imagesDir;
    private String indexDir;

    public CollectionCreatorBuilder aCreator() {
        creator = new IndexCreator(new IndexBuilder());
        return this;
    }

    public CollectionCreatorBuilder indexForFeatures(ArrayList<Feature> features) {
        this.features = features;
        return this;
    }

    public CollectionCreatorBuilder readImagesFrom(String imagesDir) {
        this.imagesDir = imagesDir;
        return this;
    }

    public CollectionCreatorBuilder openIndexIn(String indexDir) {
        this.indexDir = indexDir;
        return this;
    }

    public IndexCreator build() {
        creator.setFeatures(features);
        creator.setImagesDir(imagesDir);
        creator.setIndexDir(indexDir);
        return creator;
    }
}
