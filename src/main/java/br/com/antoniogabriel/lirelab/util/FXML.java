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
        Stage stage = createStage();
        stage.initOwner(owner);
        loadIn(stage);
    }

    public void loadIn(Stage stage) {
        try {

            Parent root = resetAndLoad();
            Scene scene = createScene(root);
            stage.setScene(scene);
            setupStage(stage);
            stage.show();
            stage.centerOnScreen();

        } catch (IOException e) {
            throw new LireLabException("Could not resetAndLoad fxml file", e);
        }
    }

    protected void setupStage(Stage stage) {
        if(stage.getOwner() != null) {
            stage.initModality(Modality.WINDOW_MODAL);
        }

        stage.setTitle(getTitle());
    }

    private Parent resetAndLoad() throws IOException {
        resetLoader();

        loader.setLocation(getClass().getResource(getFXMLResourceName()));
        Parent root = loader.load();
        controller = loader.getController();

        return root;
    }

    private void resetLoader() {
        this.loader.setRoot(null);
        this.loader.setController(null);
    }

    public <C> C getController() {
        return (C) controller;
    }

    public String getTitle() {
        return "";
    }

    public abstract String getFXMLResourceName();

    protected Scene createScene(Parent root) {
        return new Scene(root);
    }

    protected Stage createStage() {
        return new Stage();
    }
}
