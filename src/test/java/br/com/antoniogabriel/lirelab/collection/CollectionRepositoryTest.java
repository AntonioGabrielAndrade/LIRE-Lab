package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {

    private static final Collection COLLECTION1 = new Collection("Collection1");
    private static final Collection COLLECTION2 = new Collection("Collection2");
    private static final Collection COLLECTION3 = new Collection("Collection3");

    private PathResolver resolver = new PathResolver(TEST_ROOT);

    private CollectionHelper collectionHelper = new CollectionHelper(resolver);
    private CollectionRepository repository = new CollectionRepository(resolver);

    @Before
    public void setUp() throws Exception {
        collectionHelper.createStubCollection(COLLECTION1);
        collectionHelper.createStubCollection(COLLECTION2);
        collectionHelper.createStubCollection(COLLECTION3);
    }

    @After
    public void tearDown() throws Exception {
        collectionHelper.deleteCollection(COLLECTION1);
        collectionHelper.deleteCollection(COLLECTION2);
        collectionHelper.deleteCollection(COLLECTION3);

        deleteWorkDirectory();
    }

    @Test
    public void shouldGetEmptyCollectionListWhenCollectionsDirectoryDonExist() throws Exception {
        deleteWorkDirectory();

        assertTrue(repository.getCollections().isEmpty());
    }

    @Test
    public void shouldGetCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertTrue(collections.contains(COLLECTION1));
        assertTrue(collections.contains(COLLECTION2));
        assertTrue(collections.contains(COLLECTION3));
    }

    private void deleteWorkDirectory() throws IOException {
        File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
        FileUtils.deleteDirectory(directory);
    }
}
