package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.lire.Feature;
import com.google.common.base.Stopwatch;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;

import java.util.List;

class RunQueryTask extends Task<List<Image>> {

    private final CollectionService service;
    private final Collection collection;
    private final Feature feature;
    private final Image queryImage;
    private Stopwatch stop;

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
    protected List<Image> call() throws Exception {
        Stopwatch timer = Stopwatch.createStarted();
        List<Image> images = service.runQuery(collection, feature, queryImage);
        stop = timer.stop();

        return images;
    }

    public void addValueListener(ChangeListener<List<Image>> listener) {
        valueProperty().addListener(listener);
    }

    public boolean hasElapsedTime() {
        return stop != null;
    }

    public String getElapsedTime() {
        return stop.toString();
    }
}
