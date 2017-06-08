package br.com.antoniogabriel.lirelab.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class AppViewTest extends ApplicationTest {

    private AppView view;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = AppFXML.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
        view = new AppView();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkMenus();
        view.checkToolBar();
        view.checkWelcomeView();
    }




}
