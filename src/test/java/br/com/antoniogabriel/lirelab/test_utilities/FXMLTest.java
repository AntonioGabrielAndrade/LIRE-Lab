package br.com.antoniogabriel.lirelab.test_utilities;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.stage.Stage;
import org.junit.After;
import org.testfx.api.FxToolkit;

public abstract class FXMLTest<T extends FXML> extends InjectableViewTest<T> {

    @Override
    public void start(Stage stage) throws Exception {
        configStage(stage);
        fxml.loadIn(stage);
    }

    /* to be overridden by subclasses */
    protected void configStage(Stage stage) {
        stage.setMaximized(false);
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }
}
