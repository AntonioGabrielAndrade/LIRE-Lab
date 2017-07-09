package br.com.antoniogabriel.lirelab.custom.single_image_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SingleImageGrid extends StackPane {

    public static final String SINGLE_IMAGE_GRID_FXML = "single-image-grid.fxml";

    @FXML private ImageGrid imageGrid;

    private Image image;
    private List<ImageChangeListener> listeners = new ArrayList<>();

    public SingleImageGrid() {
        loadFXML();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SINGLE_IMAGE_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void setImageHeight(int height) {
        imageGrid.setImagesHeight(height);
    }

    public void setImage(Image image) {
        this.image = image;
        imageGrid.clear();
        imageGrid.addImage(image.getThumbnailPath());
        executeListeners(image);
    }

    private void executeListeners(Image image) {
        for (ImageChangeListener listener : listeners) {
            listener.changed(image);
        }
    }

    public Image getImage() {
        return image;
    }

    public void setOnChange(ImageChangeListener listener) {
        listeners.add(listener);
    }

    public void clear() {
        imageGrid.clear();
    }
}
