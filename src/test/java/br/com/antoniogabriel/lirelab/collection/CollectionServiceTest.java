package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    private CollectionService service;

    @Mock private CollectionRepository collectionRepository;

    @Before
    public void setUp() throws Exception {
        service = new CollectionService(new PathResolver(), collectionRepository);
    }

    @Test
    public void shouldGetTaskToCreateCollection() throws Exception {
        String name = "";
        String path = "";
        List<Feature> features = null;

        CreateCollectionTask task = service.getTaskToCreateCollection(name, path, features);

        assertNotNull(task);
        assertThat(task, instanceOf(CreateCollectionTask.class));
    }

    @Test
    public void shouldGetCollections() throws Exception {
        service.getCollections();
        verify(collectionRepository).getCollections();
    }
}
