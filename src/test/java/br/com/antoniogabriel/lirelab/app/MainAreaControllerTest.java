package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.CollectionGridBuilder;
import br.com.antoniogabriel.lirelab.custom.CollectionTree;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    private static final String SOME_IMAGE_PATH = "some/image/path";

    @Mock private BorderPane centerPane;
    @Mock private CollectionGridBuilder collectionGridBuilder;
    @Mock private CollectionGrid collectionGrid;
    @Mock private CollectionService collectionService;
    @Mock private CollectionTree collectionTree;
    @Mock private StackPane welcomeView;
    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private ImageViewConfig imageViewConfig;

    @InjectMocks MainAreaController controller =
            new MainAreaController(collectionService, collectionGridBuilder, imageViewFactory, imageViewConfig);

    private Collection collection;
    private List<Collection> collections;
    private List<Collection> emptyCollections = EMPTY_LIST;

    @Before
    public void setUp() throws Exception {
        collection = new Collection();
        collections = asList(new Collection("Collection1"), new Collection("Collection2"));
    }

    @Test
    public void shouldAddCollectionsAndShowTreeWhenCollectionsNotEmpty() throws Exception {
        given(collectionService.getCollections()).willReturn(collections);

        controller.initialize(null, null);

        verify(collectionService).getCollections();
        verify(collectionTree).setCollections(collections);
        verify(collectionTree).setVisible(true);
        verify(collectionTree).selectCollection(0);
        verify(welcomeView).setVisible(false);
    }

    @Test
    public void shouldShowWelcomeViewWhenCollectionsEmpty() throws Exception {
        given(collectionService.getCollections()).willReturn(emptyCollections);

        controller.initialize(null, null);

        verify(welcomeView, never()).setVisible(false);
    }

    @Test
    public void shouldListenToCollectionSelectionToShowImages() throws Exception {
        controller.initialize(null, null);

        verify(collectionTree).addCollectionSelectionListener(
                any(MainAreaController.ShowImagesWhenCollectionIsSelectedListener.class));
    }

    @Test
    public void shouldListenToImageSelectionToShowImage() throws Exception {
        controller.initialize(null, null);

        verify(collectionTree).addImageSelectionListener(
                any(MainAreaController.ShowImageWhenImageIsSelectedListener.class));
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        Collection collection = new Collection("Collection");
        given(collectionGridBuilder.build()).willReturn(collectionGrid);

        controller.showCollectionImages(collection);

        verify(collectionGrid).setCollection(collection);
        verify(centerPane).setCenter(collectionGrid);
    }

    @Test
    public void shouldShowCollectionImageWhenImageIsSelected() throws Exception {
        given(imageViewFactory.create(SOME_IMAGE_PATH)).willReturn(imageView);

        controller.showImage(SOME_IMAGE_PATH);

        verify(imageViewConfig).bindImageHeight(imageView, centerPane, 2);
        verify(centerPane).setCenter(imageView);
    }

    @Test
    public void shouldListenToCollectionChangeToReloadCollections() throws Exception {
        controller.initialize(null, null);

        verify(collectionService).addCollectionsChangeListener(
                any(MainAreaController.LoadCollectionsWhenAnyCollectionChangeListener.class)
        );
    }
}