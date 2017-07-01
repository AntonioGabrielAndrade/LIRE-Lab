package br.com.antoniogabriel.lirelab.util;

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
    }

    public void loadOwnedBy(Window owner) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(owner);
        loadIn(stage);
    }

    public void loadIn(Stage stage) throws IOException {
        Parent root = load();
        Scene scene = new Scene(root);

        if(stage.getOwner() != null) {
            stage.initModality(Modality.WINDOW_MODAL);
        }

        stage.setTitle(getTitle());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    protected Parent load() throws IOException {
        this.loader.setLocation(getClass().getResource(getFXMLResourceName()));
        this.loader.setRoot(null);
        this.loader.setController(null);

        Parent root = loader.load();

        this.loader.setLocation(null);
        return root;
    }

    public <C> C getController() throws IOException {
        load();
        return loader.getController();
    }

    public String getTitle() {
        return "";
    }

    public abstract String getFXMLResourceName();
}
