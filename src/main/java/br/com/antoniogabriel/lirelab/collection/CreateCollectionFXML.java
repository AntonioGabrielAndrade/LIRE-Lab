package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class CreateCollectionFXML extends FXML {

    public static final String FXML = "create-collection.fxml";
    public static final String TITLE = "Create Collection";

    @Inject
    public CreateCollectionFXML(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return FXML;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
