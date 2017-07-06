package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.search.SearchFXML;
import br.com.antoniogabriel.lirelab.search.SearchViewController;
import br.com.antoniogabriel.lirelab.test.FXMLTest;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.*;

public class SearchViewTest extends FXMLTest<SearchFXML> {

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);
    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private static Collection collection;

    private SearchViewController controller;
    private SearchViewObject view = new SearchViewObject();

    @BeforeClass
    public static void createCollections() throws Exception {
        startJavaFX();
        runOnFxThreadAndWait(() -> {
            collectionHelper.createRealCollection(COLLECTION_1);
            collection = collectionHelper.readCollection(COLLECTION_1.getName());
        });
    }


    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFxThreadAndWait(() -> {
            collectionHelper.deleteCollection(COLLECTION_1);
            deleteWorkDirectory(resolver);
        });
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        controller = fxml.getController();
    }

    @Test
    public void shouldShowCollection() throws Exception {
        interact(() -> controller.startSearchSession(collection, CEDD));

        view.waitUntilShowCollection(collection);
        view.selectQuery("14474347006_99aa0fd981_k");
        view.waitUntilShowQuery("14474347006_99aa0fd981_k");
//        view.waitUntilImagesOrderIs();
    }
}
