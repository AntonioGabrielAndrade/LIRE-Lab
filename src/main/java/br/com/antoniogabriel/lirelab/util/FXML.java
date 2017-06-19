package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionController;
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

    @Inject
    public FXML(FXMLLoader loader) {
        this.loader = loader;
        this.loader.setLocation(getClass().getResource(getFXMLResourceName()));
    }

    public void loadOwnedBy(Window owner) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(owner);
        loadIn(stage);
    }

    public void loadIn(Stage stage) throws IOException {
        loadIn(stage, false);
    }

    public void loadIn(Stage stage, boolean maximized) throws IOException {
        this.loader.setRoot(null);
        Parent root = loader.load();

        if(stage.getOwner() != null) {
            stage.initModality(Modality.WINDOW_MODAL);
        }
        stage.setTitle(getTitle());
        stage.setMaximized(maximized);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public <C> C getController() throws IOException {
        loader.load();
        return loader.getController();
    }

    public String getTitle() {
        return "";
    }

    public abstract String getFXMLResourceName();
}
