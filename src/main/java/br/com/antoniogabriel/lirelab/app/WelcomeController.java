package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.IOException;

public class WelcomeController {

    @FXML private Button createCollectionButton;
    @Inject private CreateCollectionFXML createCollectionFXML;

    public WelcomeController() {
        DependencyInjection.init(this);
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        createCollectionFXML.loadOwnedBy(getWindow());
    }

    private Window getWindow() {
        return createCollectionButton.getScene().getWindow();
    }
}
