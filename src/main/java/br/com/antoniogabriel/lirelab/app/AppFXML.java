package br.com.antoniogabriel.lirelab.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class AppFXML {
    private static final String FXML = "app.fxml";
    private static final String TITLE = "LIRE Lab";

    @Inject private FXMLLoader loader;

    public void loadIn(Stage stage) throws IOException {
        Parent root = loadFXML();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setMaximized(true);
        stage.show();
    }

    private Parent loadFXML() throws IOException {
        return loader.load(getClass().getResource(FXML));
    }
}
