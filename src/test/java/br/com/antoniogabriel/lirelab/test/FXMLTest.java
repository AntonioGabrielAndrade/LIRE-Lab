package br.com.antoniogabriel.lirelab.test;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.stage.Stage;
import org.junit.After;
import org.testfx.api.FxToolkit;

public abstract class FXMLTest<T extends FXML> extends InjectableViewTest<T> {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(false);
        super.start(stage);
        configStage(stage);
        fxml.loadIn(stage);
    }

    protected void configStage(Stage stage) {
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }
}
