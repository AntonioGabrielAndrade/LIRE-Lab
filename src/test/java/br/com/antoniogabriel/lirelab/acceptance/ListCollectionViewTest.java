package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.MainAreaController;
import br.com.antoniogabriel.lirelab.collection.*;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.test.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListCollectionViewTest extends FXMLTest<ListCollectionFXML> {

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
    @Mock
    private MainAreaController mainAreaController;

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
                bind(MainAreaController.class).toInstance(mainAreaController);
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

        verify(mainAreaController).showCollectionImages(COLLECTION_1);
    }
}
