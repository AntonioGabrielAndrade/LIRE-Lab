package br.com.antoniogabriel.lirelab.collection;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class CreateCollectionTaskTest {

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        CollectionCreator creator = mock(CollectionCreator.class);
        CreateCollectionTask task = new CreateCollectionTask(creator);

        verify(creator).setCallback(task);
    }

    @Test
    public void shouldCreateCollectionStepByStep() throws Exception {
        CollectionCreator creator = mock(CollectionCreator.class);
        CreateCollectionTask task = new CreateCollectionTask(creator);

        task.call();

        InOrder inOrder = inOrder(creator);
        inOrder.verify(creator).createIndex();
        inOrder.verify(creator).addImagesToIndex();
    }
}
