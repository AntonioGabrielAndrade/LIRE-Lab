package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionTaskTest {

    private CreateCollectionTask task;

    @Mock
    private IndexCreator indexCreator;

    @Mock
    private ThumbnailsCreator thumbnailsCreator;

    @Before
    public void setUp() throws Exception {
        task = new CreateCollectionTask(indexCreator, thumbnailsCreator);
    }

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        verify(indexCreator).setCallback(task);
        verify(thumbnailsCreator).setCallback(task);
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        task.call();

        verify(indexCreator).create();
        verify(thumbnailsCreator).create();
    }
}
