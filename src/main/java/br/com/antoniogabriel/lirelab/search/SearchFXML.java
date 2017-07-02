package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class SearchFXML extends FXML {

    @Inject
    public SearchFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "search.fxml";
    }
}
