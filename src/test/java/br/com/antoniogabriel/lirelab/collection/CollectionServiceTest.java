package br.com.antoniogabriel.lirelab.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    public static final PathResolver UNUSED_PATH_RESOLVER = null;
    private CollectionService service;

    @Mock private CollectionRepository collectionRepository;

    @Before
    public void setUp() throws Exception {
        service = new CollectionService(UNUSED_PATH_RESOLVER, collectionRepository);
    }

    @Test
    public void shouldGetCollections() throws Exception {
        service.getCollections();
        verify(collectionRepository).getCollections();
    }
}
