package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.util.Builder;

import javax.inject.Inject;

public class CollectionGridBuilder implements Builder<CollectionGrid> {

    private CollectionUtils collectionUtils;

    @Inject
    public CollectionGridBuilder(CollectionUtils collectionUtils) {
        this.collectionUtils = collectionUtils;
    }

    @Override
    public CollectionGrid build() {
        return new CollectionGrid(collectionUtils);
    }
}
