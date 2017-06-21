package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.FXMLTest;
import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.lire.Feature;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.collection.CollectionRepositoryTest.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.lire.Feature.*;

@RunWith(MockitoJUnitRunner.class)
public class ListCollectionViewTest extends FXMLTest<ListCollectionFXML> {

    private static final Collection COLLECTION_1 = new Collection("Collection1");
    private static final Collection COLLECTION_2 = new Collection("Collection2");
    private static final Collection COLLECTION_3 = new Collection("Collection3");
    private static final Collection COLLECTION_4 = new Collection("Collection4");

    private static final String IMAGES_PATH = TEST_ROOT + "/images";
    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private ListCollectionView view = new ListCollectionView();
    private PathResolver resolver = new PathResolver(TEST_ROOT);
    private CollectionHelper collectionHelper = new CollectionHelper(resolver);

    @Inject
    private CollectionService service;

    @Override
    public void init() throws Exception {
        interact(() -> {
            try {

                collectionHelper.createStubCollection(COLLECTION_1);
                collectionHelper.createStubCollection(COLLECTION_2);
                collectionHelper.createStubCollection(COLLECTION_3);

            } catch (IOException | JAXBException e) {
                throw new RuntimeException("Test Error", e);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        interact(() -> {
            try {

                collectionHelper.deleteCollection(COLLECTION_1.getName());
                collectionHelper.deleteCollection(COLLECTION_2.getName());
                collectionHelper.deleteCollection(COLLECTION_3.getName());
                collectionHelper.deleteCollection(COLLECTION_4.getName());

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
                                                        IMAGES_PATH,
                                                        FEATURES);

        new Thread(creationTask).start();

        view.waitUntilCollectionIsListed(COLLECTION_4);
    }

    @Test
    public void shouldUpdateCollectionTreeWhenCollectionIsDeleted() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);

        collectionHelper.deleteCollection(COLLECTION_1);

        view.waitUntilCollectionIsNotListed(COLLECTION_1);
        view.waitUntilCollectionsAreListed(COLLECTION_2, COLLECTION_3);
    }
}
