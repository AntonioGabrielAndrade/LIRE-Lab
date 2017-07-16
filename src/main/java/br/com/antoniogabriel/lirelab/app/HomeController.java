package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionContextMenuFactory;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageSelectionListener;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionRightClickListener;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionSelectionListener;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionTree;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        listenToCollectionsRightClick();
        bindCollectionsListToUI();
        loadCollections();
    }

    private void listenToCollectionSelection() {
        collectionTree.addCollectionSelectionListener(
                new ShowImagesWhenCollectionIsSelectedListener()
        );
    }

    private void listenToCollectionsRightClick() {
        collectionTree.addCollectionRightClickListener(new ShowContextMenuListener(collectionService));
    }

    private void listenToCollectionsChange() {
        collectionService.addCollectionsChangeListener(
                new LoadCollectionsWhenAnyCollectionChangeListener()
        );
    }

    private void listenToImageSelection() {
        collectionTree.addImageSelectionListener(
                new ShowImageWhenImageIsSelectedListener()
        );
    }

    private void bindCollectionsListToUI() {
        collectionTree.bindCollectionsTo(this.collections);
        collectionTree.bindVisibilityTo(this.collections.emptyProperty().not());
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

    public class ShowContextMenuListener implements CollectionRightClickListener {
        DialogProvider dialogProvider = new DialogProvider();
        CollectionContextMenuFactory factory;

        public ShowContextMenuListener(CollectionService service) {
            factory = new CollectionContextMenuFactory(service);
        }

        @Override
        public void clicked(Collection collection, MouseEvent event, Bounds itemBounds) {
            ContextMenu contextMenu = factory.createContextMenu(collection);

            double itemX = event.getX() + 60;
            double itemY = itemBounds.getMaxY() + 20;

            contextMenu.show(dialogProvider.getWindowFrom(event), itemX, itemY);
        }
    }
}
