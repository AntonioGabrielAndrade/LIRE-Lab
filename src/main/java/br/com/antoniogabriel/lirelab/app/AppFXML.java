package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class AppFXML extends FXML {

    @Inject
    public AppFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "app.fxml";
    }
}
