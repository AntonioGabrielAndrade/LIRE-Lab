package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.App;
import br.com.antoniogabriel.lirelab.app.AppView;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionView;
import br.com.antoniogabriel.lirelab.util.ProgressDialogView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;

public class LireLabAcceptanceTest extends FxRobot {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";
    private static final String TEST_IMAGES_PATH = testImagesPath();

    private CollectionHelper collectionHelper;

    private static String testImagesPath() {
        return Paths.get("src/test/resources/images/").toAbsolutePath().toString();
    }

    @Before
    public void setUpJavaFX() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App.class);
        interrupt();
    }

    @Before
    public void initObjects() throws Exception {
        collectionHelper = new CollectionHelper();
    }

    @After
    public void tearDownJavaFX() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
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


//        runner.openCreateCollectionDialog();
//        runner.fillCreateCollectionDialog(ACCEPTANCE_TEST_COLLECTION,
//                TEST_IMAGES_PATH,
//                TEST_FEATURES);

        //TODO uncomment and implement
//        runner.checkCreateCollectionProgressDialog();
//        runner.checkCollectionIsListed(ACCEPTANCE_TEST_COLLECTION);
//        runner.checkCollectionIsCreated(ACCEPTANCE_TEST_COLLECTION);
    }

}
