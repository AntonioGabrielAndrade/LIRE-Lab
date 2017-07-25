package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.inject.Inject;

public class AboutFXML extends FXML {

    @Inject
    public AboutFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "about.fxml";
    }

    @Override
    public String getTitle() {
        return "About LIRE-Lab";
    }

    @Override
    protected void setupStage(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        closeWhenLoseFocus(stage);
    }

    private void closeWhenLoseFocus(Stage stage) {
        stage.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if(!isFocused) {
                stage.close();
            }
        });
    }
}
