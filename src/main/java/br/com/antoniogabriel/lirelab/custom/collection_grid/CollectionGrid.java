package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private Collection collection;

    @FXML private ImageGrid grid;

    private EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();
    private ToolTipProvider toolTipProvider = new ToolTipProvider();

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
        setCollection(collection, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setCollection(Collection collection, ImageClickHandler handler) throws IOException {
        this.collection = collection;
        setImages(collection.getImages(), handler);
    }

    public void setImages(List<Image> images) throws IOException {
        setImages(images, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setImages(List<Image> images, ImageClickHandler handler) throws IOException {
        grid.clear();
        for (Image image : images) {
            ImageView imageView = grid.addImage(image.getThumbnailPath());
            toolTipProvider.setToolTip(imageView, image);
            imageView.setOnMouseClicked(eventHandlerFactory.createFrom(image, handler));
            imageView.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));
            imageView.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));
        }
    }

    public Collection getCollection() {
        return collection;
    }

}
