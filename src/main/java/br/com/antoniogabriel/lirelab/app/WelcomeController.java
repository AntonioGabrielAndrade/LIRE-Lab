package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        Parent root = CreateCollectionFXML.load();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
