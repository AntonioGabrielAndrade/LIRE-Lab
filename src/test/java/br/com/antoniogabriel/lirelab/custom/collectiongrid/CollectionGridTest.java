package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.imagegrid.ImageGrid;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    private static final String THUMBNAIL_PATH = "some/thumbnail/path";
    private static final String IMAGE_PATH = "some/image/path";

    @Mock private ImageGrid imageGrid;
    @Mock private ImageView imageView;

    private Collection collection = new Collection("A Collection");

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid();

    @Before
    public void setUp() throws Exception {
        collection.setImages(
                asList(
                        new Image(IMAGE_PATH, THUMBNAIL_PATH),
                        new Image(IMAGE_PATH, THUMBNAIL_PATH),
                        new Image(IMAGE_PATH, THUMBNAIL_PATH)
                )
        );
        given(imageGrid.addImage(THUMBNAIL_PATH)).willReturn(imageView);
    }

    @Test
    public void shouldAddThumbnailsToGridAndDisplayOriginalImageWhenClick() throws Exception {
        collectionGrid.setCollection(collection);

        verify(imageView, times(3)).setOnMouseClicked(any(DisplayImageDialogHandler.class));
    }

    @Test
    public void shouldClearImagesWhenSetCollection() throws Exception {
        collectionGrid.setCollection(collection);

        verify(imageGrid).clear();
    }
}