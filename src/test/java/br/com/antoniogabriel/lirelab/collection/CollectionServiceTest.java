package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    private CollectionService service;

    @Mock private CollectionRepository collectionRepository;
    @Mock private CollectionsMonitor collectionsMonitor;

    private Runnable SOME_CALLBACK = () -> {};

    @Before
    public void setUp() throws Exception {
        service = new CollectionService(new PathResolver(),
                                        collectionRepository,
                                        collectionsMonitor);
    }

    @Test
    public void shouldStartMonitoringCollections() throws Exception {
        verify(collectionsMonitor).startMonitoringCollectionsDeleteAndUpdate();
    }

    @Test
    public void shouldBuildTaskToCreateCollection() throws Exception {
        String name = "";
        String path = "";
        List<Feature> features = null;

        CreateCollectionTask task = service.getTaskToCreateCollection(name, path, features);

        verify(collectionsMonitor).bindListenersTo(task);
        assertThat(task, instanceOf(CreateCollectionTask.class));
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
