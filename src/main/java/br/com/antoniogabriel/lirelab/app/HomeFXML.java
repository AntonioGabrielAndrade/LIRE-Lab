package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class HomeFXML extends FXML{

    @Inject
    public HomeFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "home.fxml";
    }
}
