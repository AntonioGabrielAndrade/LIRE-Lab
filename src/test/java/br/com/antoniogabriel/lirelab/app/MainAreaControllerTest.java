package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.CollectionGridBuilder;
import br.com.antoniogabriel.lirelab.custom.CollectionTree;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    @Mock private BorderPane centerPane;
    @Mock private CollectionGridBuilder collectionGridBuilder;
    @Mock private CollectionGrid collectionGrid;

    @Mock private CollectionService collectionService;
    @Mock private CollectionTree collectionTree;

    @InjectMocks MainAreaController controller = new MainAreaController(collectionService, collectionGridBuilder);

    private Collection collection;
    private List<Collection> collections;

    @Before
    public void setUp() throws Exception {
        collection = new Collection();
        collections = asList(new Collection("Collection1"), new Collection("Collection2"));
    }

    @Test
    public void shouldAddCollectionsToTreeWhenInitialize() throws Exception {
        given(collectionService.getCollections()).willReturn(collections);

        controller.initialize(null, null);

        verify(collectionService).getCollections();
        verify(collectionTree).setCollections(collections);
    }

    @Test
    public void shouldListenToCollectionSelectionToShowImages() throws Exception {
        given(collectionGridBuilder.build()).willReturn(collectionGrid);

        controller.showCollectionImages(new Collection("Collection"));

        verify(centerPane).setCenter(collectionGrid);
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        controller.initialize(null, null);

        verify(collectionTree).addCollectionSelectionListener(
                any(MainAreaController.ShowImagesWhenCollectionIsSelectedListener.class));

    }

    @Test
    public void shouldListenToCollectionChangeToReloadCollections() throws Exception {
        controller.initialize(null, null);

        verify(collectionService).addCollectionsChangeListener(
                any(MainAreaController.LoadCollectionsWhenAnyCollectionChangeListener.class)
        );
    }
}