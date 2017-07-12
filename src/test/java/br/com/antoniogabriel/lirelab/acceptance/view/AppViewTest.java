package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;

public class AppViewTest extends FXMLTest<AppFXML> {

    private AppViewObject view;

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(new PathResolver(TEST_ROOT));
            }
        };
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Before
    public void setUp() throws Exception {
        view = new AppViewObject();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkMenus();
        view.checkToolBar();
        view.checkWelcomeView();
    }

    @Test
    public void shouldShowAboutInformation() throws Exception {
        view.openAboutDialog();
        view.checkAboutDialog();
    }
}
