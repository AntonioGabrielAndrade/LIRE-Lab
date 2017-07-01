package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    public static final String ANY_NAME = "";
    public static final String ANY_PATH = "";
    private CollectionService service;

    @Mock private CollectionRepository collectionRepository;
    @Mock private CollectionsMonitor collectionsMonitor;

    private Runnable SOME_CALLBACK = () -> {};

    @Before
    public void  setUp() throws Exception {
        service = new CollectionService(new PathResolver(),
                                        collectionRepository,
                                        collectionsMonitor);
    }

    @Test
    public void shouldStartMonitoringCollections() throws Exception {
        verify(collectionsMonitor).startMonitoringCollectionsDeleteAndUpdate();
    }

    @Test
    public void shouldBuildTaskToCreateCollectionAndMonitorItsCompletion() throws Exception {
        String name = ANY_NAME;
        String path = ANY_PATH;
        List<Feature> features = EMPTY_LIST;

        CreateCollectionTask task = service.getTaskToCreateCollection(name, path, features);

        assertNotNull(task);
        verify(collectionsMonitor).bindListenersTo(task);
    }

    @Test
    public void shouldGetCollections() throws Exception {
        service.getCollections();

        verify(collectionRepository).getCollections();
    }

    @Test
    public void shouldRegisterListenersForCollectionsChanges() throws Exception {
        service.addCollectionsChangeListener(SOME_CALLBACK);

        verify(collectionsMonitor).addListener(SOME_CALLBACK);
    }
}
