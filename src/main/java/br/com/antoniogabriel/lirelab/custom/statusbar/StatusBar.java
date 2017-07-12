package br.com.antoniogabriel.lirelab.custom.statusbar;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class StatusBar extends BorderPane {

    private static final String STATUS_BAR_FXML = "status-bar.fxml";

    @FXML private Label statusMessage;
    @FXML private ProgressBar statusProgress;

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

    public void setSearchStatusInfo(Collection collection, Feature feature) {
        String status = "";
        status += "Collection: " + collection.getName();
        status += "  ";
        status += "Feature: " + feature.getFeatureName();
        statusMessage.setText(status);
    }

    public void bindProgressTo(Task<?> task) {
        statusProgress.visibleProperty().bind(task.runningProperty());
    }
}
