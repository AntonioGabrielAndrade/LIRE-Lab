package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.IOException;

public abstract class FXML {

    private FXMLLoader loader;
    private Object controller;

    @Inject
    public FXML(FXMLLoader loader) {
        this.loader = loader;
    }

    public void loadOwnedBy(Window owner) {
        Stage stage = new Stage();
        stage.initOwner(owner);
        loadIn(stage);
    }

    public void loadIn(Stage stage) {
        try {
            Parent root = load();
            Scene scene = new Scene(root);

            if(stage.getOwner() != null) {
                stage.initModality(Modality.WINDOW_MODAL);
            }

            stage.setTitle(getTitle());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new LireLabException("Could not load fxml file", e);
        }
    }

    private Parent load() throws IOException {
        this.loader.setLocation(getClass().getResource(getFXMLResourceName()));
        this.loader.setRoot(null);
        this.loader.setController(null);

        Parent root = loader.load();
        controller = loader.getController();

        this.loader.setLocation(null);
        return root;
    }

    public <C> C getController() throws IOException {
        return (C) controller;
    }

    public String getTitle() {
        return "";
    }

    public abstract String getFXMLResourceName();
}
