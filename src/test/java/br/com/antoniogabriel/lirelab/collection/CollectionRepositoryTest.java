package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {

    private static final Collection COLLECTION1 = getCollectionNamed("Collection1");
    private static final Collection COLLECTION2 = getCollectionNamed("Collection2");
    private static final Collection COLLECTION3 = getCollectionNamed("Collection3");

    public static final String TEST_ROOT = "src/test/resources";

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
        collectionHelper.deleteCollection(COLLECTION1.getName());
        collectionHelper.deleteCollection(COLLECTION2.getName());
        collectionHelper.deleteCollection(COLLECTION3.getName());

        File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
        FileUtils.deleteDirectory(directory);
    }

    @Test
    public void shouldGetCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertThat(collections.size(), is(3));
        assertThat(collections.contains(COLLECTION1), is(true));
        assertThat(collections.contains(COLLECTION2), is(true));
        assertThat(collections.contains(COLLECTION3), is(true));
    }

    private static Collection getCollectionNamed(String name) {
        Collection collection = new Collection();
        collection.setName(name);

        return collection;
    }
}
