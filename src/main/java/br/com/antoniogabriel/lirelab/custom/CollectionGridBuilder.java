package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.util.Builder;

import javax.inject.Inject;

public class CollectionGridBuilder implements Builder<CollectionGrid> {

    private PathResolver resolver;
    private ImageGridBuilder imageGridBuilder;

    @Inject
    public CollectionGridBuilder(PathResolver resolver, ImageGridBuilder imageGridBuilder) {
        this.resolver = resolver;
        this.imageGridBuilder = imageGridBuilder;
    }

    @Override
    public CollectionGrid build() {
        return new CollectionGrid(resolver, imageGridBuilder);
    }
}
