package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button createCollectionButton;

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        CreateCollectionFXML.showOwnedBy(getWindow());
    }

    private Window getWindow() {
        return createCollectionButton.getScene().getWindow();
    }
}
