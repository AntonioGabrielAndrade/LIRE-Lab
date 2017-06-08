package br.com.antoniogabriel.lirelab.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionTaskTest {

    @Mock private IndexCreator creator;

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        CreateCollectionTask task = new CreateCollectionTask(creator);

        verify(creator).setCallback(task);
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        CreateCollectionTask task = new CreateCollectionTask(creator);

        task.call();

        verify(creator).create();
    }
}
