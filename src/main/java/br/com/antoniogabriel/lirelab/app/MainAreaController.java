package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
import br.com.antoniogabriel.lirelab.custom.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.CollectionGridBuilder;
import br.com.antoniogabriel.lirelab.custom.CollectionSelectionListener;
import br.com.antoniogabriel.lirelab.custom.CollectionTree;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class MainAreaController implements Initializable {

    private CollectionService collectionService;
    private CollectionGridBuilder collectionGridBuilder;

    @FXML private BorderPane centerPane;
    @FXML private CollectionTree collectionTree;

    @Inject
    public MainAreaController(CollectionService collectionService, CollectionGridBuilder collectionGridBuilder) {
        this.collectionService = collectionService;
        this.collectionGridBuilder = collectionGridBuilder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listenToCollectionSelection();
        listenToCollectionsChange();
        loadCollections();
    }

    protected void listenToCollectionsChange() {
        collectionService.addCollectionsChangeListener(
                new LoadCollectionsWhenAnyCollectionChangeListener()
        );
    }

    protected void listenToCollectionSelection() {
        collectionTree.addCollectionSelectionListener(
                new ShowImagesWhenCollectionIsSelectedListener()
        );
    }

    private void loadCollections() {
        collectionTree.setCollections(collectionService.getCollections());
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

    class ShowImagesWhenCollectionIsSelectedListener implements CollectionSelectionListener {
        @Override
        public void selected(Collection collection) {
            MainAreaController.this.showCollectionImages(collection);
        }
    }

    class LoadCollectionsWhenAnyCollectionChangeListener implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> MainAreaController.this.loadCollections());
        }
    }
}
