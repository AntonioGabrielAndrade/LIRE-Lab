package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageGrid extends StackPane {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;
    private List<String> paths;

    private int imagesHeight;

    @FXML private FlowPane flowPane;

    @Inject
    public ImageGrid(ImageViewFactory imageViewFactory, FileUtils fileUtils) {
        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "image-grid.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void setImages(List<String> paths) throws FileNotFoundException {
        this.paths = paths;

        paths.size();

        for (String path : paths) {
            if(fileUtils.fileExists(path)) {
                ImageView imageView = imageViewFactory.create(path);
                imageView.setFitHeight(imagesHeight);
                imageView.setPreserveRatio(true);
                images().add(imageView);
            }
        }

    }

    protected ObservableList<Node> images() {
        ObservableList<Node> nodes = flowPane.getChildren();
        return nodes;
    }

    public List<String> getPaths() {
        return Collections.unmodifiableList(paths);
    }

    public void setImagesHeight(int imagesHeight) {
        this.imagesHeight = imagesHeight;
    }

    public int getImagesHeight() {
        return imagesHeight;
    }
}
