package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

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
}
