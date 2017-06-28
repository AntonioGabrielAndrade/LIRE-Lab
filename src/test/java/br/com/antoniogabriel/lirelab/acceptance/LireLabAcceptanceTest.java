package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;

import java.io.IOException;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;

public class LireLabAcceptanceTest extends ApplicationTest {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";

    @Inject private ApplicationRunner app;
    @Inject private CollectionHelper collectionHelper;
    @Inject private AppFXML fxml;


    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this);
        new FxRobot().interact(() -> {
            try {
                fxml.loadIn(stage, true);
            } catch (IOException e) {
                throw new LireLabException("Could not load fxml", e);
            }
        });
    }

    @After
    public void cleanEnvironment() throws Exception {
        collectionHelper.deleteCollection(ACCEPTANCE_TEST_COLLECTION);
        FxToolkit.hideStage();
    }

    @Test
    public void userJourneyTest() throws Exception {
        app.createCollection(ACCEPTANCE_TEST_COLLECTION, TEST_IMAGES, CEDD, TAMURA);
        app.viewCollection(ACCEPTANCE_TEST_COLLECTION);
    }
}
