package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionContextMenuFactory;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionTree;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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

    private SimpleListProperty<Collection> collections = new SimpleListProperty<>();

    private CollectionService collectionService;

    private ImageViewFactory viewFactory;
    private ApplicationCommands applicationCommands;
    private ImageViewConfig viewConfig;

    @FXML private BorderPane centerPane;
    @FXML private CollectionTree collectionTree;
    @FXML private StackPane welcomeView;


    @Inject
    public HomeController(CollectionService collectionService,
                          ImageViewFactory viewFactory,
                          ApplicationCommands applicationCommands,
                          ImageViewConfig viewConfig) {

        this.collectionService = collectionService;
        this.viewFactory = viewFactory;
        this.applicationCommands = applicationCommands;
        this.viewConfig = viewConfig;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listenToCollectionSelection();
        listenToImageSelection();
        listenToCollectionsChange();
        bindCollectionsListToUI();
        loadCollections();
        setCollectionTreeContextMenu();
    }

    private void listenToCollectionSelection() {
        collectionTree.addCollectionSelectionListener(collection -> showCollectionImages(collection));
        collectionTree.addCollectionSelectionListener(collection -> {
            CollectionContextMenuFactory factory = new CollectionContextMenuFactory(applicationCommands.getCollectionCommands());
            collectionTree.setContextMenu(factory.createContextMenu(collection));
        });
        collectionTree.collectionsProperty().emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
            if(isEmpty) {
                setCollectionTreeContextMenu();
            }
        });
    }

    private void setCollectionTreeContextMenu() {
        CollectionTreeContextMenuFactory factory = new CollectionTreeContextMenuFactory(applicationCommands.getCollectionTreeContextMenuCommands());
        collectionTree.setContextMenu(factory.createContextMenu());
    }

    private void listenToCollectionsChange() {
        collectionService.addCollectionsChangeListener(() -> Platform.runLater(() -> loadCollections()));
    }

    private void listenToImageSelection() {
        collectionTree.addImageSelectionListener(imagePath -> {
            if(imagePath != null)
                showImage(imagePath);
        });
    }

    private void bindCollectionsListToUI() {
        collectionTree.bindCollectionsTo(this.collections);
        welcomeView.visibleProperty().bind(this.collections.emptyProperty());

        this.collections.emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
            if(isEmpty) {
                centerPane.setCenter(welcomeView);
            } else {
                collectionTree.selectCollection(0);
            }
        });
    }

    private void loadCollections() {
        List<Collection> collections = collectionService.getCollections();
        this.collections.setValue(FXCollections.observableArrayList(collections));
        collectionTree.selectCollection(0);
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
}
