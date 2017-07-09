package br.com.antoniogabriel.lirelab.test_utilities;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.stage.Stage;

import javax.inject.Inject;

public class ControllerTest<T extends FXML, C> extends InjectableViewTest<T> {

    @Inject
    protected C controller;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        controller = fxml.getController();
    }
}
