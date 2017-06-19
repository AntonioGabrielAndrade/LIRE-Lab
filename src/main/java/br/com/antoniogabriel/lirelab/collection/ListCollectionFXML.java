package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class ListCollectionFXML extends FXML {

    @Inject
    public ListCollectionFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getTitle() {
        return "Collections";
    }

    @Override
    public String getFXMLResourceName() {
        return "collection-list.fxml";
    }
}
