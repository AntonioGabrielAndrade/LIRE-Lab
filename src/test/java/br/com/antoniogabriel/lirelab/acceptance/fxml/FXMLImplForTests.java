package br.com.antoniogabriel.lirelab.acceptance.fxml;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.fxml.FXMLLoader;

public class FXMLImplForTests extends FXML {

    public FXMLImplForTests(FXMLLoader loader) {
        super(loader);
    }

    @Override
    public String getFXMLResourceName() {
        return "fxml-impl-for-tests.fxml";
    }
}
