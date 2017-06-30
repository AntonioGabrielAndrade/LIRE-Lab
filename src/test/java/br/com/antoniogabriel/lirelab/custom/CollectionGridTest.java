package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.scene.image.ImageView;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    private static final String THUMBNAIL_PATH = "some/thumbnail/path";
    private static final String IMAGE_PATH = "some/image/path";

    @Mock private ImageGrid imageGrid;
    @Mock private CollectionUtils collectionUtils;
    @Mock private ImageView imageView;

    private List<String> thumbnailsPaths = EMPTY_LIST;
    private Collection collection = new Collection("A Collection");

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid(collectionUtils);

    @Before
    public void setUp() throws Exception {
        collection.setImages(
                asList(
                        new Image(IMAGE_PATH, THUMBNAIL_PATH),
                        new Image(IMAGE_PATH, THUMBNAIL_PATH),
                        new Image(IMAGE_PATH, THUMBNAIL_PATH)
                )
        );
    }

    @Test
    public void shouldAddThumbnailsToGridAndDisplayOriginalImageWhenClick() throws Exception {
        given(imageGrid.addImage(THUMBNAIL_PATH)).willReturn(imageView);

        collectionGrid.setCollection(collection);

        verify(imageView, times(3)).setOnMouseClicked(any(DisplayImageDialogHandler.class));
    }
}