package br.com.antoniogabriel.lirelab;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeViewController {
    public static final String CREATE_COLLECTION = "Create Collection";

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(CREATE_COLLECTION);
        Parent root = FXMLLoader.load(
                getClass().getClassLoader().getResource(
                        "br/com/antoniogabriel/lirelab/collection/create-collection.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.centerOnScreen();
        stage.show();
    }
}
