package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.ImageChangeListener;
import br.com.antoniogabriel.lirelab.lire.Feature;

class QueryChangeListener implements ImageChangeListener {

    private final Collection collection;
    private final Feature feature;
    private SearchController controller;

    public QueryChangeListener(SearchController controller,
                               Collection collection,
                               Feature feature) {

        this.controller = controller;
        this.collection = collection;
        this.feature = feature;
    }

    @Override
    public void changed(Image queryImage) {
        controller.runQuery(collection, feature, queryImage);
    }
}
