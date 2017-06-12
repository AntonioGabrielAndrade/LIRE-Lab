package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.App;
import br.com.antoniogabriel.lirelab.app.AppView;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionView;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.ProgressDialogView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.nio.file.Paths;

public class LireLabAcceptanceTest extends FxRobot {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";
    private static final String TEST_IMAGES_PATH = testImagesPath();

    private static String testImagesPath() {
        return Paths.get("src/test/resources/images/").toAbsolutePath().toString();
    }

    @Before
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App.class);
        interrupt();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        AppView appView = new AppView();
        CreateCollectionView createView = appView.createCollection();

        createView
                .writeName(ACCEPTANCE_TEST_COLLECTION)
                .writeImagesDirectory(TEST_IMAGES_PATH)
                .selectFeatures(Feature.CEDD, Feature.TAMURA);

        ProgressDialogView progressView = createView.create();

        progressView
                //indexing images
                .checkProgressMark(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
                //creating thumbnails
                .checkProgressMark(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
                .checkOkIsEnabledWhenFinish()
                .ok();


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
