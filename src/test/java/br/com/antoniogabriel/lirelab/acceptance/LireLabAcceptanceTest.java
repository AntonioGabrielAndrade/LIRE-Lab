package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.Feature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LireLabAcceptanceTest {

    private static final String TEST_COLLECTION_NAME = "Test Collection";
    private static final String TEST_IMAGES_PATH = "br/com/antoniogabriel/lirelab/acceptance/test-images";
    private static final Feature[] TEST_FEATURES = new Feature[]{Feature.CEDD, Feature.TAMURA};

    private ApplicationRunner runner = new ApplicationRunner();

    @Before
    public void setUp() throws Exception {
        runner.setUpApp();
    }

    @After
    public void tearDown() throws Exception {
        runner.tearDownApp();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        runner.checkMenus();
        runner.checkToolBar();
        runner.checkWelcomeView();
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        runner.openCreateCollectionDialog();
        runner.fillCreateCollectionDialog(TEST_COLLECTION_NAME,
                TEST_IMAGES_PATH,
                TEST_FEATURES);
        runner.checkCreateCollectionProgressDialog();
        runner.checkListTestCollection();
        runner.checkCollectionIsCreated(TEST_COLLECTION_NAME);
    }
}
