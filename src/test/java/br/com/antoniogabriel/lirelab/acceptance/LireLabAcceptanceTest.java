package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.app.AppView;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionView;
import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import br.com.antoniogabriel.lirelab.util.ProgressDialogView;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;
import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;

public class LireLabAcceptanceTest extends ApplicationTest {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";
    private static final String TEST_IMAGES_PATH = testImagesPath();

    private CollectionHelper collectionHelper;

    private static String testImagesPath() {
        return Paths.get("src/test/resources/images/").toAbsolutePath().toString();
    }

    @Inject
    private AppFXML fxml;

    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this);
        fxml.loadIn(stage, true);
    }

    @Before
    public void initObjects() throws Exception {
        collectionHelper = new CollectionHelper();
    }

    @After
    public void cleanEnvironment() throws Exception {
        collectionHelper.deleteCollection(ACCEPTANCE_TEST_COLLECTION);
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        AppView appView = new AppView();
        CreateCollectionView createView = appView.createCollection();

        createView
                .writeName(ACCEPTANCE_TEST_COLLECTION)
                .writeImagesDirectory(TEST_IMAGES_PATH)
                .selectFeatures(CEDD, TAMURA);

        ProgressDialogView progressView = createView.create();

        progressView
                //indexing images
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                //creating thumbnails
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                .checkOkIsEnabledWhenFinish()
                .ok();

        collectionHelper.checkCollectionExists(ACCEPTANCE_TEST_COLLECTION);

        appView.checkCollectionIsListed(ACCEPTANCE_TEST_COLLECTION);

    }
}
