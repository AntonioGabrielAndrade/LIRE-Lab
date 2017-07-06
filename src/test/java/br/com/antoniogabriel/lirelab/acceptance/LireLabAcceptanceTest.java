package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;

public class LireLabAcceptanceTest extends FXMLTest<AppFXML> {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";

    private PathResolver resolver = new PathResolver();
    private CollectionHelper collectionHelper = new CollectionHelper(resolver);
    private ApplicationRunner app = new ApplicationRunner(collectionHelper);

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @After
    public void cleanEnvironment() throws Exception {
        collectionHelper.deleteCollection(ACCEPTANCE_TEST_COLLECTION);
    }

    @Test
    public void userJourneyTest() throws Exception {
        app.createCollection(ACCEPTANCE_TEST_COLLECTION, TEST_IMAGES, CEDD, TAMURA);
        app.viewCollection(ACCEPTANCE_TEST_COLLECTION);
        app.searchCollection(ACCEPTANCE_TEST_COLLECTION);
    }
}
