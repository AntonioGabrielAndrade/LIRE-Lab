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

    @Mock IndexCreator indexCreator;
    @Mock ThumbnailsCreator thumbnailsCreator;
    @Mock XMLCreator xmlCreator;

    @Before
    public void setUp() throws Exception {
        task = new CreateCollectionTask(indexCreator, thumbnailsCreator, xmlCreator);
    }

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        verify(indexCreator).setCallback(task);
        verify(thumbnailsCreator).setCallback(task);
        verify(xmlCreator).setCallback(task);
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        task.call();

        verify(indexCreator).create();
        verify(thumbnailsCreator).create();
        verify(xmlCreator).create();
    }
}
