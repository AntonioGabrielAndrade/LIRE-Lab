package br.com.antoniogabriel.lirelab.custom.statusbar;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class StatusBar extends BorderPane {

    private static final String STATUS_BAR_FXML = "status-bar.fxml";

    @FXML private Label statusMessage;
    @FXML private ProgressBar statusProgress;
    @FXML private ComboBox<Feature> featuresComboBox;

    public StatusBar() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STATUS_BAR_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void bindProgressTo(Task<?> task) {
        statusProgress.visibleProperty().bind(task.runningProperty());
    }

    public void setFeatures(List<Feature> features, FeatureSelectionListener listener) {
        featuresComboBox.setItems(FXCollections.observableList(features));
        featuresComboBox.valueProperty().addListener((observable, oldFeature, newFeature) -> {
            listener.selected(newFeature);
        });
    }

    public void selectFeature(Feature feature) {
        featuresComboBox.valueProperty().setValue(feature);
    }
}
