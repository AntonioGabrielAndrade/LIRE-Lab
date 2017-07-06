package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageSelectionListener;
import br.com.antoniogabriel.lirelab.custom.collectiontree.CollectionSelectionListener;
import br.com.antoniogabriel.lirelab.custom.collectiontree.CollectionTree;
import br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class HomeController implements Initializable {

    public static final int DEFAULT_COLLECTION_PAGE_SIZE = 120;

    private CollectionService collectionService;
    private ImageViewFactory viewFactory;
    private ImageViewConfig viewConfig;

    @FXML private BorderPane centerPane;
    @FXML private CollectionTree collectionTree;
    @FXML private StackPane welcomeView;


    @Inject
    public HomeController(CollectionService collectionService,
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

            PaginatedCollectionGrid grid = new PaginatedCollectionGrid();
            grid.setPageSize(DEFAULT_COLLECTION_PAGE_SIZE);
            centerPane.setCenter(grid);
            grid.setCollection(collection);

        } catch (IOException e) {
            throw new LireLabException("Could not show collections", e);
        }
    }

    public void showImage(String imagePath) {
        ImageView image = viewFactory.create(imagePath);
        viewConfig.bindImageHeight(image, centerPane, 0.8);
        centerPane.setCenter(image);
    }

    public Collection getSelectedCollection() {
        return collectionTree.getSelectedCollection();
    }

    class ShowImagesWhenCollectionIsSelectedListener implements CollectionSelectionListener {
        @Override
        public void selected(Collection collection) {
            HomeController.this.showCollectionImages(collection);
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
            Platform.runLater(() -> HomeController.this.loadCollections());
        }
    }

}
