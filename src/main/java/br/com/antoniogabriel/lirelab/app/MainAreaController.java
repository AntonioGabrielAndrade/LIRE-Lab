package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
import br.com.antoniogabriel.lirelab.custom.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.CollectionGridBuilder;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class MainAreaController {

    private CollectionGridBuilder collectionGridBuilder;

    @FXML private BorderPane centerPane;


    @Inject
    public MainAreaController(CollectionGridBuilder collectionGridBuilder) {
        this.collectionGridBuilder = collectionGridBuilder;
    }

    public void showCollectionImages(Collection collection) {
        try {

            CollectionGrid grid = collectionGridBuilder.build();
            grid.setCollection(collection);
            centerPane.setCenter(grid);

        } catch (IOException e) {
            throw new LireLabException("Could not show collections", e);
        }
    }

}
