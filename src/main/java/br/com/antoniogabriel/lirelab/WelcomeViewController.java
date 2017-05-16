package br.com.antoniogabriel.lirelab;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeViewController {
    public static final String CREATE_COLLECTION = "Create Collection";

    public void openCreateCollectionDialog(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle(CREATE_COLLECTION);
        stage.setScene(new Scene(new StackPane(), 600, 400));
        stage.centerOnScreen();
        stage.show();
    }
}
