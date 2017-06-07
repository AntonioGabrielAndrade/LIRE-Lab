package br.com.antoniogabriel.lirelab.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionTaskTest {

    @Mock private CollectionCreator creator;

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        CreateCollectionTask task = new CreateCollectionTask(creator);

        verify(creator).setCallback(task);
    }

    @Test
    public void shouldCreateCollectionStepByStep() throws Exception {
        CreateCollectionTask task = new CreateCollectionTask(creator);

        task.call();

        InOrder inOrder = inOrder(creator);
        inOrder.verify(creator).createIndex();
        inOrder.verify(creator).addImagesToIndex();
    }
}
