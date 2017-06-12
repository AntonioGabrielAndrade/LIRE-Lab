package br.com.antoniogabriel.lirelab.collection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CreateCollectionFXML {

    public static final String TITLE = "Create Collection";

    public static void showOwnedBy(Window owner) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(owner);
        showIn(stage);
    }

    public static void showIn(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(
                CreateCollectionFXML.class.getResource("create-collection.fxml"));

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
