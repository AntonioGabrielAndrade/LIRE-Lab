package br.com.antoniogabriel.lirelab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String MAIN_FXML_PATH = "br/com/antoniogabriel/lirelab/main/main.fxml";

    public static void main(String[] args) {
        launch(Main.class);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource(MAIN_FXML_PATH));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LIRE Lab");
        stage.setMaximized(true);
        stage.show();
    }

}
