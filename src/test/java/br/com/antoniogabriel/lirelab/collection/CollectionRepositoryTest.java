package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.embed.swing.JFXPanel;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.api.FxRobot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {

    private static final Collection COLLECTION1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION3 = collection("Collection3", TEST_IMAGES, CEDD);

    private static final PathResolver resolver = new PathResolver(TEST_ROOT);

    private static final CollectionHelper collectionHelper = new CollectionHelper(resolver);
    private static final CollectionRepository repository = new CollectionRepository(resolver);

    @BeforeClass
    public static void createCollections() throws Exception {
        startJavaFX();
        runOnFXThread(() -> {
            try {

                collectionHelper.createRealCollection(COLLECTION1);
                collectionHelper.createRealCollection(COLLECTION2);
                collectionHelper.createRealCollection(COLLECTION3);

            } catch (Exception e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFXThread(() -> {
            try {

                collectionHelper.deleteCollection(COLLECTION1);
                collectionHelper.deleteCollection(COLLECTION2);
                collectionHelper.deleteCollection(COLLECTION3);

                deleteWorkDirectory();

            } catch (IOException e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @Test
    public void shouldGetEmptyCollectionListWhenCollectionsDirectoryDonExist() throws Exception {
        deleteWorkDirectory();

        assertTrue(repository.getCollections().isEmpty());

        createCollections();

        assertFalse(repository.getCollections().isEmpty());
    }

    @Test
    public void shouldGetCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertThat(collections.size(), is(3));
        assertTrue(collections.contains(COLLECTION1));
        assertTrue(collections.contains(COLLECTION2));
        assertTrue(collections.contains(COLLECTION3));
    }

    @Test
    public void shouldGetCollectionsWithImagesPaths() throws Exception {
        List<Collection> collections = repository.getCollections();

        List<String> paths = collections.get(0).getImagePaths();

        String imagesDir = collections.get(0).getImagesDirectory();

        assertThat(paths.size(), is(10));

        assertTrue(paths.contains(imagesDir + "14474347006_99aa0fd981_k.jpg"));
        assertTrue(paths.contains(imagesDir + "16903390174_1d670a5849_h.jpg"));
        assertTrue(paths.contains(imagesDir + "17099294578_0ba4068bad_k.jpg"));
        assertTrue(paths.contains(imagesDir + "17338370170_1e620bfb18_h.jpg"));
        assertTrue(paths.contains(imagesDir + "17525978165_86dc26e8cb_h.jpg"));
        assertTrue(paths.contains(imagesDir + "19774866363_757555901c_k.jpg"));
        assertTrue(paths.contains(imagesDir + "25601366680_b57441bb52_k.jpg"));
        assertTrue(paths.contains(imagesDir + "25601374660_78e6a9bba8_k.jpg"));
        assertTrue(paths.contains(imagesDir + "26487616294_b22b87133e_k.jpg"));
        assertTrue(paths.contains(imagesDir + "26489383923_98d419eb0d_k.jpg"));
    }

    private static void deleteWorkDirectory() throws IOException {
        File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
        FileUtils.deleteDirectory(directory);
    }

    private static Collection collection(String name, String imagesPath, Feature... features) {
        Collection collection = new Collection(name);
        collection.setImagesDirectory(imagesPath);
        collection.setFeatures(asList(features));
        return collection;
    }

    private static void startJavaFX() {
        new JFXPanel();
    }

    private static void runOnFXThread(Runnable runnable) {
        new FxRobot().interact(runnable);
    }
}
