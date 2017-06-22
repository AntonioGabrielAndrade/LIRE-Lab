package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.App;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class AppViewTest extends ApplicationTest {

    private AppViewObject view;

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    @Before
    public void setUp() throws Exception {
        view = new AppViewObject();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkMenus();
        view.checkToolBar();
        view.checkWelcomeView();
    }

}
