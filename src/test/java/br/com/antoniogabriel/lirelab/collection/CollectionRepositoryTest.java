package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.*;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {

    private static final Collection COLLECTION1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION3 = collection("Collection3", TEST_IMAGES, CEDD);

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);

    private static final CollectionHelper COLLECTION_HELPER = new CollectionHelper(RESOLVER);
    private static final CollectionRepository REPOSITORY = new CollectionRepository(RESOLVER);

    @BeforeClass
    public static void createCollections() throws Exception {
        startJavaFX();
        runOnFXThread(() -> {
            try {

                COLLECTION_HELPER.createRealCollection(COLLECTION1);
                COLLECTION_HELPER.createRealCollection(COLLECTION2);
                COLLECTION_HELPER.createRealCollection(COLLECTION3);

            } catch (Exception e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        runOnFXThread(() -> {
            try {

                COLLECTION_HELPER.deleteCollection(COLLECTION1);
                COLLECTION_HELPER.deleteCollection(COLLECTION2);
                COLLECTION_HELPER.deleteCollection(COLLECTION3);

                deleteWorkDirectory(RESOLVER);

            } catch (IOException e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @Test
    public void shouldGetEmptyCollectionListWhenCollectionsDirectoryDonExist() throws Exception {
        deleteWorkDirectory(RESOLVER);

        assertTrue(REPOSITORY.getCollections().isEmpty());

        createCollections();

        assertFalse(REPOSITORY.getCollections().isEmpty());
    }

    @Test
    public void shouldGetCollectionsFromDisk() throws Exception {
        List<Collection> collections = REPOSITORY.getCollections();

        assertThat(collections.size(), is(3));
        assertTrue(collections.contains(COLLECTION1));
        assertTrue(collections.contains(COLLECTION2));
        assertTrue(collections.contains(COLLECTION3));
    }

    @Test
    public void shouldGetCollectionsWithImagesPaths() throws Exception {
        List<Collection> collections = REPOSITORY.getCollections();

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

}
