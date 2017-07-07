package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.search.SearchFXML;
import br.com.antoniogabriel.lirelab.search.SearchViewController;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;

public class SearchViewTest extends FXMLTest<SearchFXML> {

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);
    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private static Collection collection;

    private SearchViewController controller;
    private SearchViewObject view = new SearchViewObject();

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(resolver);
            }
        };
    }

    @BeforeClass
    public static void createCollections() throws Exception {
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

        view.waitUntilImagesAreOrderedLike("14474347006_99aa0fd981_k",
                                            "17338370170_1e620bfb18_h",
                                            "26489383923_98d419eb0d_k",
                                            "25601374660_78e6a9bba8_k",
                                            "17525978165_86dc26e8cb_h",
                                            "26487616294_b22b87133e_k",
                                            "16903390174_1d670a5849_h",
                                            "17099294578_0ba4068bad_k",
                                            "25601366680_b57441bb52_k",
                                            "19774866363_757555901c_k");
    }
}
