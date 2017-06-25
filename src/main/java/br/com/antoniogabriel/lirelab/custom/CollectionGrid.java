package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private Collection collection;
    private ImageGridBuilder imageGridBuilder;
    private CollectionUtils collectionUtils;

    @FXML private StackPane root;

    public CollectionGrid(ImageGridBuilder imageGridBuilder, CollectionUtils collectionUtils) {
        this.imageGridBuilder = imageGridBuilder;
        this.collectionUtils = collectionUtils;

        loadFXML();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COLLECTION_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setCollection(Collection collection) throws IOException {
        this.collection = collection;
        ImageGrid imageGrid = imageGridBuilder.build();
        imageGrid.setImages(collectionUtils.getThumbnailsPaths(collection));
        root.getChildren().add(imageGrid);
    }

    public Collection getCollection() {
        return collection;
    }
}
