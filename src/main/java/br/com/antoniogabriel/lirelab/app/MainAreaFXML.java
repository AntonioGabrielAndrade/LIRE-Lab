package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class MainAreaFXML extends FXML{

    @Inject
    public MainAreaFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "main-area.fxml";
    }
}
