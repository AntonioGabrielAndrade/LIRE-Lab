package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private Collection collection;
    private CollectionUtils collectionUtils;
    private ImageViewFactory imageViewFactory = new ImageViewFactory();

    @FXML private ImageGrid grid;

    public CollectionGrid(CollectionUtils collectionUtils) {
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
        for (Image image : collection.getImages()) {
            ImageView imageView = grid.addImage(image.getThumbnailPath());
            imageView.setOnMouseClicked(new DisplayImageInDialogHandler(image));
        }
    }

    public Collection getCollection() {
        return collection;
    }

    class DisplayImageInDialogHandler implements EventHandler<MouseEvent> {

        private Image image;

        public DisplayImageInDialogHandler(Image image) {
            this.image = image;
        }

        @Override
        public void handle(MouseEvent event) {
            try {
                ImageDialog dialog = new ImageDialog(image.getImagePath());
                dialog.initOwner(new DialogProvider().getWindowFrom(event));
                dialog.show();

            } catch (FileNotFoundException e) {
                throw new LireLabException("Error displaying image", e);
            }
        }
    }
}
