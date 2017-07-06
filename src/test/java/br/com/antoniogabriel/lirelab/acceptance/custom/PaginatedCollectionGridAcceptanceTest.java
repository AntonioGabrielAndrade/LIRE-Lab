package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid.PaginatedCollectionGrid;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.*;

public class PaginatedCollectionGridAcceptanceTest extends ApplicationTest {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionHelper COLLECTION_HELPER = new CollectionHelper(RESOLVER);
    public static final int PAGE_SIZE = 3;

    private static Collection collection = collection("Collection", TEST_IMAGES, CEDD);

    private PaginatedCollectionGrid collectionGrid;
    private PaginatedGridViewObject view = new PaginatedGridViewObject();

    @BeforeClass
    public static void createCollections() throws Exception {
        startJavaFX();
        runOnFxThreadAndWait(() -> {
            try {
                COLLECTION_HELPER.createRealCollection(collection);
                collection = COLLECTION_HELPER.readCollection(collection.getName());
            } catch (Exception e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            try {
                COLLECTION_HELPER.deleteCollection(collection);
                deleteWorkDirectory(RESOLVER);
            } catch (IOException e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        collectionGrid = new PaginatedCollectionGrid();
        collectionGrid.setPageSize(PAGE_SIZE);
        collectionGrid.setCollection(collection);

        Scene scene = new Scene(collectionGrid, 900, 200);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowImagesPaginated() throws Exception {
        List<Image> images = collection.getImages();

        view.checkImagesAreVisible(images.get(0), images.get(1), images.get(2));

        view.nextPage();

        view.checkImagesAreVisible(images.get(3), images.get(4), images.get(5));

        view.nextPage();

        view.checkImagesAreVisible(images.get(6), images.get(7), images.get(8));

        view.nextPage();

        view.checkImagesAreVisible(images.get(9));
    }

}
