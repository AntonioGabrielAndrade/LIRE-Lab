package br.com.antoniogabriel.lirelab.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    public static final String CREATE_COLLECTION = "Create Collection";

    @FXML
    private Button createCollectionButton;

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(CREATE_COLLECTION);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(createCollectionButton.getScene().getWindow());
        Parent root = FXMLLoader.load(
                getClass().getClassLoader().getResource(
                        "br/com/antoniogabriel/lirelab/collection/create-collection.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.centerOnScreen();
        stage.show();
    }
}
