package br.com.antoniogabriel.lirelab.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AppFXML {
    public static Parent load() throws IOException {
        return FXMLLoader.load(
                AppFXML.class.getResource("app.fxml"));
    }
}
