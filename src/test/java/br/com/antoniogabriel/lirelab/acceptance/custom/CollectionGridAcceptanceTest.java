package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.custom.collection_grid.CollectionGrid;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class CollectionGridAcceptanceTest extends ApplicationTest {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionHelper COLLECTION_HELPER = new CollectionHelper(RESOLVER);

    private static Collection collection = collection("Collection", TEST_IMAGES, CEDD);

    private CollectionGrid collectionGrid;

    @BeforeClass
    public static void createCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            COLLECTION_HELPER.createRealCollection(collection);
            collection = COLLECTION_HELPER.readCollection(collection.getName());
        });
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            COLLECTION_HELPER.deleteCollection(collection);
            deleteWorkDirectory(RESOLVER);
        });
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        collectionGrid = new CollectionGrid();
        collectionGrid.setCollection(collection);

        Scene scene = new Scene(collectionGrid, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowImages() throws Exception {
        verifyThat("#14474347006_99aa0fd981_k", isVisible());
        verifyThat("#16903390174_1d670a5849_h", isVisible());
        verifyThat("#17099294578_0ba4068bad_k", isVisible());
        verifyThat("#17338370170_1e620bfb18_h", isVisible());
        verifyThat("#17525978165_86dc26e8cb_h", isVisible());
        verifyThat("#19774866363_757555901c_k", isVisible());
        verifyThat("#25601366680_b57441bb52_k", isVisible());
        verifyThat("#25601374660_78e6a9bba8_k", isVisible());
        verifyThat("#26487616294_b22b87133e_k", isVisible());
        verifyThat("#26489383923_98d419eb0d_k", isVisible());
    }

    @Test
    public void shouldDisplayImageInDialogWhenClicked() throws Exception {
        clickOn("#14474347006_99aa0fd981_k");

        Node imageNode = from(lookup("#image-dialog")).lookup("#14474347006_99aa0fd981_k").query();
        verifyThat(imageNode, isVisible());
    }
}
