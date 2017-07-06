package br.com.antoniogabriel.lirelab.test_utilities;

import br.com.antoniogabriel.lirelab.util.FXML;
import javafx.stage.Stage;
import org.junit.After;
import org.testfx.api.FxToolkit;

public abstract class FXMLTest<T extends FXML> extends InjectableViewTest<T> {

    @Override
    public void start(Stage stage) throws Exception {
        interact(() -> {
            try {
                super.start(stage);
                stage.setMaximized(false);
                configStage(stage);
                fxml.loadIn(stage);
            } catch (Exception e) {
                throw new RuntimeException("Error", e);
            }
        });
    }

    protected void configStage(Stage stage) { /* to be overridden by subclasses */ }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }
}
