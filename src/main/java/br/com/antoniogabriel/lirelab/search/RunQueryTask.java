package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;

class RunQueryTask extends Task<Collection> {

    private final CollectionService service;
    private final Collection collection;
    private final Feature feature;
    private final Image queryImage;

    public RunQueryTask(CollectionService service,
                        Collection collection,
                        Feature feature,
                        Image queryImage) {

        this.service = service;
        this.collection = collection;
        this.feature = feature;
        this.queryImage = queryImage;
    }

    @Override
    protected Collection call() throws Exception {
        return service.runQuery(collection, feature, queryImage);
    }

    public void addValueListener(ChangeListener<Collection> listener) {
        valueProperty().addListener(listener);
    }
}
