package br.com.antoniogabriel.lirelab.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = AppFXML.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LIRE Lab");
        stage.setMaximized(true);
        stage.show();
    }
}
