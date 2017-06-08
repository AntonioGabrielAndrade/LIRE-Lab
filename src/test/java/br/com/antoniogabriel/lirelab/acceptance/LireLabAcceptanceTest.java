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

public class LireLabAcceptanceTest extends FxRobot {

    private static final String TEST_COLLECTION_NAME = "Test Collection";
    private static final String TEST_IMAGES_PATH = "br/com/antoniogabriel/lirelab/acceptance/test-images";
    private static final Feature[] TEST_FEATURES = {Feature.CEDD, Feature.TAMURA};

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
        CreateCollectionView createView = appView.openCreateCollectionView();

        createView.writeName(TEST_COLLECTION_NAME)
                .writeImagesDirectory(TEST_IMAGES_PATH)
                .selectFeatures(TEST_FEATURES);

        ProgressDialogView progressView = createView.create();


//        runner.openCreateCollectionDialog();
//        runner.fillCreateCollectionDialog(TEST_COLLECTION_NAME,
//                TEST_IMAGES_PATH,
//                TEST_FEATURES);

        //TODO uncomment and implement
//        runner.checkCreateCollectionProgressDialog();
//        runner.checkCollectionIsListed(TEST_COLLECTION_NAME);
//        runner.checkCollectionIsCreated(TEST_COLLECTION_NAME);
    }

}
