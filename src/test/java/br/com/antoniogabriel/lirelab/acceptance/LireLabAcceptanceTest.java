package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.app.AppViewObject;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionViewObject;
import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import br.com.antoniogabriel.lirelab.util.ProgressDialogViewObject;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;

public class LireLabAcceptanceTest extends ApplicationTest {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";

    @Inject
    private CollectionHelper collectionHelper;

    @Inject
    private AppFXML fxml;

    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this);
        fxml.loadIn(stage, true);
    }

    @After
    public void cleanEnvironment() throws Exception {
        collectionHelper.deleteCollection(ACCEPTANCE_TEST_COLLECTION);
        FxToolkit.hideStage();
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        AppViewObject appView = new AppViewObject();
        CreateCollectionViewObject createView = appView.createCollection();

        createView
                .writeName(ACCEPTANCE_TEST_COLLECTION)
                .writeImagesDirectory(TEST_IMAGES)
                .selectFeatures(CEDD, TAMURA);

        ProgressDialogViewObject progressView = createView.create();

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
