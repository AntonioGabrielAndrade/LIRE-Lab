package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.SimpleIndexCreator;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionTaskTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private CreateCollectionTask task;

    @Mock private SimpleIndexCreator indexCreator;
    @Mock private ThumbnailsCreator thumbnailsCreator;
    @Mock private XMLCreator xmlCreator;

    @Before
    public void setUp() throws Exception {
        task = new CreateCollectionTask(new CreateCollectionRunnable(indexCreator, thumbnailsCreator, xmlCreator));
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
