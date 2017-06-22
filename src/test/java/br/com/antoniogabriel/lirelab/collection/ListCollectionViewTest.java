package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.test.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import javafx.embed.swing.JFXPanel;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.api.FxRobot;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;

@RunWith(MockitoJUnitRunner.class)
public class ListCollectionViewTest extends FXMLTest<ListCollectionFXML> {

    private static final Collection COLLECTION_1 = new Collection("Collection1");
    private static final Collection COLLECTION_2 = new Collection("Collection2");
    private static final Collection COLLECTION_3 = new Collection("Collection3");
    private static final Collection COLLECTION_4 = new Collection("Collection4");

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);
    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private ListCollectionView view = new ListCollectionView();


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

                FileUtils.deleteDirectory(new File(resolver.getWorkDirectoryPath()));

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
    public void shouldUpdateCollectionTreeWhenNewCollectionIsCreated() throws Exception {
        CreateCollectionTask creationTask = service.getTaskToCreateCollection(
                                                        COLLECTION_4.getName(),
                                                        TEST_IMAGES,
                                                        FEATURES);

        new Thread(creationTask).start();

        view.waitUntilCollectionIsListed(COLLECTION_4);
    }

    @Test
    public void shouldListImagesInCollectionItem() throws Exception {
        view.expandCollection(COLLECTION_1);
        view.checkImageIsVisible("14474347006_99aa0fd981_k.jpg");
    }

    @Test
    public void shouldUpdateCollectionTreeWhenCollectionIsDeleted() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);

        collectionHelper.deleteCollection(COLLECTION_1);

        view.waitUntilCollectionIsNotListed(COLLECTION_1);
        view.waitUntilCollectionsAreListed(COLLECTION_2, COLLECTION_3);
    }

    private static void startJavaFX() {
        new JFXPanel();
    }

    private static void runOnFXThread(Runnable runnable) {
        new FxRobot().interact(runnable);
    }
}
