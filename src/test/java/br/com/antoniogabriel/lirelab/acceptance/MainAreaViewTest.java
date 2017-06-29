package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.MainAreaFXML;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionTask;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.test.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import javafx.scene.Node;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaViewTest extends FXMLTest<MainAreaFXML>{

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_3 = collection("Collection3", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_4 = collection("Collection4", TEST_IMAGES, CEDD);

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);
    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private ListCollectionViewObject view = new ListCollectionViewObject();

    @Inject
    private CollectionService service;

    @BeforeClass
    public static void createCollections() throws Exception {
        startJavaFX();
        runOnFXThread(() -> {
            try {

                collectionHelper.createRealCollection(COLLECTION_1);
                collectionHelper.createRealCollection(COLLECTION_2);
                collectionHelper.createRealCollection(COLLECTION_3);

            } catch (Exception e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }


    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFXThread(() -> {
            try {

                collectionHelper.deleteCollection(COLLECTION_1);
                collectionHelper.deleteCollection(COLLECTION_2);
                collectionHelper.deleteCollection(COLLECTION_3);
                collectionHelper.deleteCollection(COLLECTION_4);

                deleteWorkDirectory(resolver);

            } catch (IOException e) {
                throw new RuntimeException("Test Error", e);
            }
        });
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

    @Test
    public void shouldListCollections() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        view.expandCollection(COLLECTION_1);

        view.checkImageIsListed("14474347006_99aa0fd981_k.jpg");
        view.checkImageIsListed("16903390174_1d670a5849_h.jpg");
        view.checkImageIsListed("17099294578_0ba4068bad_k.jpg");
        view.checkImageIsListed("17338370170_1e620bfb18_h.jpg");
        view.checkImageIsListed("17525978165_86dc26e8cb_h.jpg");
        view.checkImageIsListed("19774866363_757555901c_k.jpg");
        view.checkImageIsListed("25601366680_b57441bb52_k.jpg");
        view.checkImageIsListed("25601374660_78e6a9bba8_k.jpg");
        view.checkImageIsListed("26487616294_b22b87133e_k.jpg");
        view.checkImageIsListed("26489383923_98d419eb0d_k.jpg");
    }

    @Test
    public void shouldUpdateCollectionsListWhenNewCollectionIsCreated() throws Exception {
        CreateCollectionTask creationTask = service.getTaskToCreateCollection(
                COLLECTION_4.getName(),
                TEST_IMAGES,
                FEATURES);

        new Thread(creationTask).start();

        view.waitUntilCollectionIsListed(COLLECTION_4);
    }

    @Test
    public void shouldUpdateCollectionsListWhenCollectionIsDeleted() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);

        collectionHelper.deleteCollection(COLLECTION_1);

        view.waitUntilCollectionIsNotListed(COLLECTION_1);
        view.waitUntilCollectionsAreListed(COLLECTION_2, COLLECTION_3);

        runOnFXThread(() -> collectionHelper.createRealCollection(COLLECTION_1));
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        view.selectCollection(COLLECTION_1);

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
    public void shouldShowImageWhenImageIsSelected() throws Exception {
        view.expandCollection(COLLECTION_1);
        view.selectImage("14474347006_99aa0fd981_k.jpg");

        Node image = from(lookup("#center-pane")).lookup("#14474347006_99aa0fd981_k").query();
        verifyThat(image, isVisible());
    }
}
