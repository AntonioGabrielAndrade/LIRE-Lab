package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String THUMBNAIL_PATH = "some/thumbnail/path";
    private static final String IMAGE_PATH = "some/image/path";
    private static final Image IMAGE = new Image(IMAGE_PATH, THUMBNAIL_PATH);

    @Mock private ImageGrid imageGrid;
    @Mock private ImageView imageView;
    @Mock private ImageClickHandler imageClickHandler;
    @Mock private EventHandlerFactory eventHandlerFactory;
    @Mock private EventHandler<MouseEvent> eventHandler;
    @Mock private ToolTipProvider toolTipProvider;

    private Collection collection = new Collection("A Collection");

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid();

    @Before
    public void setUp() throws Exception {
        collection.setImages(asList(IMAGE, IMAGE, IMAGE));
        given(imageGrid.addImage(THUMBNAIL_PATH)).willReturn(imageView);
    }

    @Test
    public void shouldAddThumbnailsToGridAndDisplayOriginalImageWhenClick() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), any(DisplayImageDialogHandler.class)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection);

        verify(imageView, times(3)).setOnMouseClicked(eventHandler);
    }

    @Test
    public void shouldAddToolTipToImage() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), any(DisplayImageDialogHandler.class)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection);

        verify(toolTipProvider, times(3)).setToolTip(imageView, IMAGE.getImageName());
    }

    @Test
    public void shouldClearImagesWhenSetCollection() throws Exception {
        collectionGrid.setCollection(collection);

        verify(imageGrid).clear();
    }

    @Test
    public void shouldRegisterImageClickHandlerWithCollectionImages() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), eq(imageClickHandler)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection, imageClickHandler);

        verify(imageView, times(3)).setOnMouseClicked(eventHandler);
    }
}