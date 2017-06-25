package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.util.Builder;

import javax.inject.Inject;

public class CollectionGridBuilder implements Builder<CollectionGrid> {

    private ImageGridBuilder imageGridBuilder;
    private CollectionUtils collectionUtils;

    @Inject
    public CollectionGridBuilder(ImageGridBuilder imageGridBuilder, CollectionUtils collectionUtils) {
        this.imageGridBuilder = imageGridBuilder;
        this.collectionUtils = collectionUtils;
    }

    @Override
    public CollectionGrid build() {
        return new CollectionGrid(imageGridBuilder, collectionUtils);
    }
}
