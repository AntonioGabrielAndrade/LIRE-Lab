package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.app.HomeFXML;
import br.com.antoniogabriel.lirelab.collection.*;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.isHeadless;

public class HomeViewTest extends FXMLTest<HomeFXML> {

    private static final String COLLECTION1_NAME = "Collection1";
    private static final String COLLECTION2_NAME = "Collection2";
    private static final String COLLECTION3_NAME = "Collection3";

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private static Collection collection1;
    private static Collection collection2;

    private HomeViewObject homeView = new HomeViewObject();

    @Inject
    private CollectionService service;

    @BeforeClass
    public static void createCollections() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
        COLLECTION_HELPER.createRealCollection(COLLECTION2_NAME, TEST_IMAGES, CEDD);

        collection1 = COLLECTION_HELPER.readCollection(COLLECTION1_NAME);
        collection2 = COLLECTION_HELPER.readCollection(COLLECTION2_NAME);
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        COLLECTION_HELPER.deleteCollection(COLLECTION1_NAME);
        COLLECTION_HELPER.deleteCollection(COLLECTION2_NAME);
        COLLECTION_HELPER.deleteCollection(COLLECTION3_NAME);
    }


    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(RESOLVER);
            }
        };
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Test
    public void shouldListCollections() throws Exception {
        homeView.waitUntilCollectionsAreListed(collection1, collection2);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.expandCollection(collection1);

        homeView.waitUntilImageIsListed("14474347006_99aa0fd981_k.jpg");
        homeView.waitUntilImageIsListed("16903390174_1d670a5849_h.jpg");
        homeView.waitUntilImageIsListed("17099294578_0ba4068bad_k.jpg");
        homeView.waitUntilImageIsListed("17338370170_1e620bfb18_h.jpg");
        homeView.waitUntilImageIsListed("17525978165_86dc26e8cb_h.jpg");
        homeView.waitUntilImageIsListed("19774866363_757555901c_k.jpg");
        homeView.waitUntilImageIsListed("25601366680_b57441bb52_k.jpg");
        homeView.waitUntilImageIsListed("25601374660_78e6a9bba8_k.jpg");
        homeView.waitUntilImageIsListed("26487616294_b22b87133e_k.jpg");
        homeView.waitUntilImageIsListed("26489383923_98d419eb0d_k.jpg");
    }

    @Test
    public void shouldUpdateCollectionsListWhenNewCollectionIsCreated() throws Exception {
        CreateCollectionRunner runner = service.getCreateCollectionRunner(
                                                        new CreateCollectionInfo(
                                                                COLLECTION3_NAME,
                                                                TEST_IMAGES,
                                                                FEATURES,
                                                                true,
                                                                false,
                                                                1));

        new Thread(runner).start();

        homeView.waitUntilCollectionIsListed(COLLECTION3_NAME);
    }

    @Test
    public void shouldUpdateCollectionsListWhenCollectionIsDeleted() throws Exception {
        homeView.waitUntilCollectionsAreListed(collection1, collection2);

        COLLECTION_HELPER.deleteCollection(collection1);

        homeView.waitUntilCollectionIsNotListed(collection1);
        homeView.waitUntilCollectionsAreListed(collection2);

        COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
    }

    @Test
    public void shouldDeleteCollectionByContextMenu() throws Exception {
        // Nasty hack to execute test only if not in headless mode.
        // Apparently Monocle cant show ContextMenu in headless mode.
        if(!isHeadless()) {
            homeView.waitUntilCollectionsAreListed(collection1, collection2);

            homeView.deleteByContextMenu(collection1).ok();

            homeView.waitUntilCollectionIsNotListed(collection1);
            homeView.waitUntilCollectionsAreListed(collection2);

            COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
        }
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.selectCollection(collection1);

        homeView.waitUntilImageIsVisible("14474347006_99aa0fd981_k");
        homeView.waitUntilImageIsVisible("16903390174_1d670a5849_h");
        homeView.waitUntilImageIsVisible("17099294578_0ba4068bad_k");
        homeView.waitUntilImageIsVisible("17338370170_1e620bfb18_h");
        homeView.waitUntilImageIsVisible("17525978165_86dc26e8cb_h");
        homeView.waitUntilImageIsVisible("19774866363_757555901c_k");
        homeView.waitUntilImageIsVisible("25601366680_b57441bb52_k");
        homeView.waitUntilImageIsVisible("25601374660_78e6a9bba8_k");
        homeView.waitUntilImageIsVisible("26487616294_b22b87133e_k");
        homeView.waitUntilImageIsVisible("26489383923_98d419eb0d_k");
    }

    @Test
    public void shouldShowImageWhenImageIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.expandCollection(collection1);
        homeView.selectImage("14474347006_99aa0fd981_k.jpg");
        homeView.waitUntilImageIsVisible("14474347006_99aa0fd981_k");
    }
}
