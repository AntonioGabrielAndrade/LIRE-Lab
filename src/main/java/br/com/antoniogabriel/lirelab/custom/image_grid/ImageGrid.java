package br.com.antoniogabriel.lirelab.custom.image_grid;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static javafx.beans.binding.Bindings.createObjectBinding;

public class ImageGrid extends StackPane {

    public static final String IMAGE_GRID_FXML = "image-grid.fxml";

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;

    @FXML private FlowPane flowPane;

    private SimpleIntegerProperty imagesHeight = new SimpleIntegerProperty(100);

    public ImageGrid() {
        this(new ImageViewFactory(), new FileUtils());
    }

    @Inject
    public ImageGrid(ImageViewFactory imageViewFactory, FileUtils fileUtils) {
        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;

        loadFXML();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(IMAGE_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void bindGapsTo(DoubleProperty property) {
        flowPane.hgapProperty().bind(property);
        flowPane.vgapProperty().bind(property);
        flowPane.paddingProperty().bind(createObjectBinding(() -> new Insets(property.doubleValue()), property));
    }


    public void setPaths(List<String> paths) {
        for (String path : paths) {
            addImage(path);
        }
    }

    public ImageView addImage(String path) {
        if(fileUtils.fileExists(path)) {
            return addImageToGrid(path);
        }
        return null;
    }

    private ImageView addImageToGrid(String path) {
        ImageView imageView = imageViewFactory.create(path);
        imageView.fitHeightProperty().bind(imagesHeight);
        imageView.setPreserveRatio(true);
        images().add(imageView);
        return imageView;
    }

    private ObservableList<Node> images() {
        return flowPane.getChildren();
    }

    public void setImagesHeight(int imagesHeight) {
        this.imagesHeight.set(imagesHeight);
    }

    public void clear() {
        images().clear();
    }

    public void bindImagesHeightTo(DoubleProperty property) {
        imagesHeight.bind(property);
    }
}
