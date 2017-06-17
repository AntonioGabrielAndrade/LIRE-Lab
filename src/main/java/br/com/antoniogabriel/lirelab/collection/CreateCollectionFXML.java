package br.com.antoniogabriel.lirelab.collection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.IOException;

public class CreateCollectionFXML {

    public static final String FXML = "create-collection.fxml";
    public static final String TITLE = "Create Collection";

    @Inject private FXMLLoader loader;

    public void showOwnedBy(Window owner) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(owner);
        showIn(stage);
    }

    public void showIn(Stage stage) throws IOException {
        Parent root = loader.load(getClass().getResource(FXML));

        if(stage.getOwner() != null) {
            stage.initModality(Modality.WINDOW_MODAL);
        }
        stage.setTitle(TITLE);
        stage.setMaximized(false);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

}
