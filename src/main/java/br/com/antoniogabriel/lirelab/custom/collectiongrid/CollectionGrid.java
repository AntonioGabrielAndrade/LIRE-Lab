package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.ImageGrid;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private Collection collection;

    @FXML private ImageGrid grid;

    public CollectionGrid() {
        loadFXML();
    }

    private void loadFXML() {
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
        for (Image image : collection.getImages()) {
            ImageView imageView = grid.addImage(image.getThumbnailPath());
            imageView.setOnMouseClicked(new DisplayImageDialogHandler(image, new DialogProvider(), new FileUtils()));
        }
    }

    public Collection getCollection() {
        return collection;
    }

}
