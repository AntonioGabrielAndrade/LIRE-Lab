package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
import br.com.antoniogabriel.lirelab.custom.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.CollectionGridBuilder;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    @Mock private BorderPane centerPane;
    @Mock private CollectionGridBuilder collectionGridBuilder;
    @Mock private CollectionGrid collectionGrid;

    @InjectMocks MainAreaController controller = new MainAreaController(collectionGridBuilder);

    private Collection collection;

    @Before
    public void setUp() throws Exception {
        collection = new Collection();
    }

    @Test(expected = LireLabException.class)
    public void shouldThrowCustomExceptionWhenIOExceptionOccurs() throws Exception {
        given(collectionGridBuilder.build()).willReturn(collectionGrid);
        doThrow(IOException.class).when(collectionGrid).setCollection(collection);

        controller.showCollectionImages(collection);
    }

    @Test
    public void shouldAddCollectionGridToCenter() throws Exception {
        given(collectionGridBuilder.build()).willReturn(collectionGrid);

        controller.showCollectionImages(collection);

        verify(collectionGrid).setCollection(collection);
        verify(centerPane).setCenter(collectionGrid);
    }
}