package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    @Mock private ImageGrid imageGrid;
    @Mock private CollectionUtils collectionUtils;

    private List<String> thumbnailsPaths = EMPTY_LIST;
    private Collection collection;

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid(collectionUtils);


    @Test
    public void shouldAddCollectionThumbnailsToGrid() throws Exception {
        given(collectionUtils.getThumbnailsPaths(collection)).willReturn(thumbnailsPaths);

        collectionGrid.setCollection(collection);

        verify(imageGrid).setImages(thumbnailsPaths);
    }
}