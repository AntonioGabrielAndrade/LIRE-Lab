package br.com.antoniogabriel.lirelab.test;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.stage.Stage;

public abstract class FXMLTest<T extends FXML> extends InjectableViewTest<T> {

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        configStage(stage);
        fxml.loadIn(stage);
    }

    protected void configStage(Stage stage) {
    }
}
