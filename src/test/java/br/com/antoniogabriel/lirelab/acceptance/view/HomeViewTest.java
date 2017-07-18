package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.app.HomeFXML;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionTask;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
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
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.collection;

public class HomeViewTest extends FXMLTest<HomeFXML>{

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_3 = collection("Collection3", TEST_IMAGES, CEDD);

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);
    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private HomeViewObject homeView = new HomeViewObject();

    @Inject
    private CollectionService service;

    @BeforeClass
    public static void createCollections() throws Exception {
        collectionHelper.createRealCollection(COLLECTION_1);
        collectionHelper.createRealCollection(COLLECTION_2);
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        collectionHelper.deleteCollection(COLLECTION_1);
        collectionHelper.deleteCollection(COLLECTION_2);
        collectionHelper.deleteCollection(COLLECTION_3);
    }


    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(resolver);
            }
        };
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Test
    public void shouldListCollections() throws Exception {
        homeView.waitUntilCollectionsAreListed(COLLECTION_1, COLLECTION_2);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        homeView.waitUntilCollectionIsListed(COLLECTION_1);
        homeView.expandCollection(COLLECTION_1);

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
        CreateCollectionTask creationTask = service.getTaskToCreateCollection(
                                                            COLLECTION_3.getName(),
                                                            TEST_IMAGES,
                                                            FEATURES,
                                                            true,
                                                            false,
                                                            1);

        new Thread(creationTask).start();

        homeView.waitUntilCollectionIsListed(COLLECTION_3);
    }

    @Test
    public void shouldUpdateCollectionsListWhenCollectionIsDeleted() throws Exception {
        homeView.waitUntilCollectionsAreListed(COLLECTION_1, COLLECTION_2);

        collectionHelper.deleteCollection(COLLECTION_1);

        homeView.waitUntilCollectionIsNotListed(COLLECTION_1);
        homeView.waitUntilCollectionsAreListed(COLLECTION_2);

        collectionHelper.createRealCollection(COLLECTION_1);
    }

    @Test
    public void shouldDeleteCollectionByContextMenu() throws Exception {
        homeView.waitUntilCollectionsAreListed(COLLECTION_1, COLLECTION_2);

        homeView.deleteCollection(COLLECTION_1);

        homeView.waitUntilCollectionIsNotListed(COLLECTION_1);
        homeView.waitUntilCollectionsAreListed(COLLECTION_2);

        collectionHelper.createRealCollection(COLLECTION_1);
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(COLLECTION_1);
        homeView.selectCollection(COLLECTION_1);

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
        homeView.waitUntilCollectionIsListed(COLLECTION_1);
        homeView.expandCollection(COLLECTION_1);
        homeView.selectImage("14474347006_99aa0fd981_k.jpg");
        homeView.waitUntilImageIsVisible("14474347006_99aa0fd981_k");
    }
}
