package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.*;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class MainAreaController implements Initializable {

    private CollectionService collectionService;
    private ImageViewFactory viewFactory;
    private ImageViewConfig viewConfig;

    @FXML private BorderPane centerPane;
    @FXML private CollectionTree collectionTree;
    @FXML private StackPane welcomeView;


    @Inject
    public MainAreaController(CollectionService collectionService,
                              ImageViewFactory viewFactory,
                              ImageViewConfig viewConfig) {

        this.collectionService = collectionService;
        this.viewFactory = viewFactory;
        this.viewConfig = viewConfig;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listenToCollectionSelection();
        listenToImageSelection();
        listenToCollectionsChange();
        loadCollections();
    }


    private void listenToCollectionsChange() {
        collectionService.addCollectionsChangeListener(
                new LoadCollectionsWhenAnyCollectionChangeListener()
        );
    }

    private void listenToCollectionSelection() {
        collectionTree.addCollectionSelectionListener(
                new ShowImagesWhenCollectionIsSelectedListener()
        );
    }

    private void listenToImageSelection() {
        collectionTree.addImageSelectionListener(
                new ShowImageWhenImageIsSelectedListener()
        );
    }

    private void loadCollections() {
        List<Collection> collections = collectionService.getCollections();
        if(!collections.isEmpty()) {
            collectionTree.setCollections(collections);
            collectionTree.setVisible(true);
            collectionTree.selectCollection(0);
            welcomeView.setVisible(false);
        }
    }

    public void showCollectionImages(Collection collection) {
        try {

            CollectionGrid grid = new CollectionGrid();
            grid.setCollection(collection);
            centerPane.setCenter(grid);

        } catch (IOException e) {
            throw new LireLabException("Could not show collections", e);
        }
    }

    public void showImage(String imagePath) {
        try {

            ImageView image = viewFactory.create(imagePath);
            viewConfig.bindImageHeight(image, centerPane, 0.8);
            centerPane.setCenter(image);

        } catch (FileNotFoundException e) {
            throw new LireLabException("Could not show image", e);
        }

    }

    class ShowImagesWhenCollectionIsSelectedListener implements CollectionSelectionListener {
        @Override
        public void selected(Collection collection) {
            MainAreaController.this.showCollectionImages(collection);
        }
    }

    class ShowImageWhenImageIsSelectedListener implements ImageSelectionListener {
        @Override
        public void selected(String imagePath) {
            if(imagePath != null)
                showImage(imagePath);
        }
    }

    class LoadCollectionsWhenAnyCollectionChangeListener implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> MainAreaController.this.loadCollections());
        }
    }

}
