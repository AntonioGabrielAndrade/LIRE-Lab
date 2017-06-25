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
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    @Mock BorderPane centerPane;
    @Mock CollectionGridBuilder collectionGridBuilder;
    @Mock CollectionGrid collectionGrid;

    @InjectMocks MainAreaController controller = new MainAreaController(collectionGridBuilder);

    private Collection collection;
    private List<String> paths;

    @Before
    public void setUp() throws Exception {
        paths = new ArrayList<>(asList("path1", "path2", "path3"));
        collection = new Collection();
        collection.setImagePaths(paths);
    }

    @Test(expected = LireLabException.class)
    public void shouldThrowCustomExceptionIfIOExceptionOccurs() throws Exception {
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