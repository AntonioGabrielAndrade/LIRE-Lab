package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.app.Feature;

import java.util.ArrayList;

public class IndexCreatorBuilder {
    private IndexCreator creator;
    private ArrayList<Feature> features;
    private String imagesDir;
    private String indexDir;

    public IndexCreatorBuilder aCreator() {
        creator = new IndexCreator(new IndexBuilder());
        return this;
    }

    public IndexCreatorBuilder indexForFeatures(ArrayList<Feature> features) {
        this.features = features;
        return this;
    }

    public IndexCreatorBuilder readImagesFrom(String imagesDir) {
        this.imagesDir = imagesDir;
        return this;
    }

    public IndexCreatorBuilder openIndexIn(String indexDir) {
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
