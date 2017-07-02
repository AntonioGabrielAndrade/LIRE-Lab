package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.fxml.FXML;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class SearchViewController {

    @FXML private CollectionGrid grid;

    public void startSearchSession(Collection collection) {
        try {

            grid.setCollection(collection);

        } catch (IOException e) {
            throw new LireLabException("Could not show collection", e);
        }
    }
}
