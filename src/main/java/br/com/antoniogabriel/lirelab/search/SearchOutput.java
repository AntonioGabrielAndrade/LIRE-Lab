package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.FeatureSelectionListener;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class SearchOutput extends BorderPane {

    private static final String SEARCH_OUTPUT_FXML = "search-output.fxml";

    @FXML private PaginatedCollectionGrid outputGrid;
    @FXML private StatusBar statusBar;
    @FXML private HBox titleGraphics;

    public SearchOutput() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SEARCH_OUTPUT_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addTitleGraphics(Node node) {
        titleGraphics.getChildren().add(node);
    }

    public void bindProgressTo(Task<?> task, String message) {
        statusBar.bindProgressTo(task, message);
    }

    public void setCollection(Collection collection) throws IOException {
        outputGrid.setCollection(collection);
    }

    public void setCollection(Collection collection, ImageClickHandler handler) {
        outputGrid.setCollection(collection, handler);
    }

    public void setCollection(List<Image> images, ImageClickHandler handler) {
        outputGrid.setCollection(images, handler);
    }

    public void setMessage(String message) {
        statusBar.setMessage(message);
    }

    public Feature getSelectedFeature() {
        return statusBar.getSelectedFeature();
    }

    public void clear() {
        statusBar.clear();
    }

    public void setFeatures(List<Feature> features, Feature defaultFeature, FeatureSelectionListener listener) {
        statusBar.setFeatures(features, defaultFeature, listener);
    }

    public void disableTitleGraphics() {
        titleGraphics.setDisable(true);
    }

    public void enableTitleGraphics() {
        titleGraphics.setDisable(false);
    }
}
