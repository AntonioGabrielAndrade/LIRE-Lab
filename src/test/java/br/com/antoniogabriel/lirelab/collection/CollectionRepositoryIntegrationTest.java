package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import br.com.antoniogabriel.lirelab.util.LireLabUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryIntegrationTest {


    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);
    private static final CollectionUtils COLLECTION_UTILS = new CollectionUtils(RESOLVER);
    private static final FileUtils FILE_UTILS = new FileUtils();
    private static final CollectionAssembler COLLECTION_ASSEMBLER = new CollectionAssembler(RESOLVER, COLLECTION_UTILS);
    private static final LireLabUtils LIRE_LAB_UTILS = new LireLabUtils(RESOLVER, FILE_UTILS);

    private static final String COLLECTION1_NAME = "Collection1";
    private static final String COLLECTION2_NAME = "Collection2";

    private static Collection collection1;
    private static Collection collection2;

    private CollectionRepository repository = new CollectionRepository(LIRE_LAB_UTILS, COLLECTION_ASSEMBLER);

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

        deleteWorkDirectory(RESOLVER);
    }

    @Test
    public void shouldReturnEmptyCollectionListWhenCollectionsDirectoryDonExist() throws Exception {
        deleteWorkDirectory(RESOLVER);

        assertTrue(repository.getCollections().isEmpty());

        createCollections();

        assertFalse(repository.getCollections().isEmpty());
    }

    @Test
    public void shouldReturnAllCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertThat(collections.size(), is(2));
        assertTrue(collections.contains(collection1));
        assertTrue(collections.contains(collection2));
    }

    @Test
    public void shouldReturnSpecificCollectionFromDisk() throws Exception {
        Collection collection = repository.getCollection(COLLECTION1_NAME);

        assertThat(collection.getName(), equalTo(COLLECTION1_NAME));
        assertThat(collection.getImages().size(), is(10));
        assertTrue(collection.getFeatures().contains(CEDD));
    }

    @Test
    public void shouldReturnCollectionsWithImagesInfo() throws Exception {
        List<Collection> collections = repository.getCollections();

        Collection collection = collections.get(0);

        List<Image> images = collection.getImages();
        String imagesDir = collection.getImagesDirectory();
        String thumbnailsDir = RESOLVER.getThumbnailsDirectoryPath(collection.getName()) + "/";

        assertThat(images.size(), is(10));

        images.contains(new Image(imagesDir + "14474347006_99aa0fd981_k.jpg", thumbnailsDir + "14474347006_99aa0fd981_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "16903390174_1d670a5849_h.jpg", thumbnailsDir + "16903390174_1d670a5849_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17099294578_0ba4068bad_k.jpg", thumbnailsDir + "17099294578_0ba4068bad_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17338370170_1e620bfb18_h.jpg", thumbnailsDir + "17338370170_1e620bfb18_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "17525978165_86dc26e8cb_h.jpg", thumbnailsDir + "17525978165_86dc26e8cb_h.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "19774866363_757555901c_k.jpg", thumbnailsDir + "19774866363_757555901c_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "25601366680_b57441bb52_k.jpg", thumbnailsDir + "25601366680_b57441bb52_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "25601374660_78e6a9bba8_k.jpg", thumbnailsDir + "25601374660_78e6a9bba8_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "26487616294_b22b87133e_k.jpg", thumbnailsDir + "26487616294_b22b87133e_k.thumbnail.jpg"));
        images.contains(new Image(imagesDir + "26489383923_98d419eb0d_k.jpg", thumbnailsDir + "26489383923_98d419eb0d_k.thumbnail.jpg"));
    }

    @Test
    public void shouldDeleteCollection() throws Exception {
        String testCollection = "TestCollection";
        COLLECTION_HELPER.createRealCollection(testCollection, TEST_IMAGES, CEDD);
        COLLECTION_HELPER.checkCollectionExists(testCollection);

        repository.deleteCollection(testCollection);

        COLLECTION_HELPER.checkCollectionDontExists(testCollection);
    }
}
