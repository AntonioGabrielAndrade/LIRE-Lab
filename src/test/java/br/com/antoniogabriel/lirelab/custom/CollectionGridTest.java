package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
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
    @Mock private ImageGridBuilder imageGridBuilder;
    @Mock private StackPane root;
    @Mock private ObservableList<Node> children;
    @Mock private CollectionUtils collectionUtils;

    private List<String> thumbnailsPaths = EMPTY_LIST;
    private Collection collection;

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid(imageGridBuilder, collectionUtils);


    @Test
    public void shouldAddCollectionThumbnailsToGrid() throws Exception {
        given(root.getChildren()).willReturn(children);
        given(imageGridBuilder.build()).willReturn(imageGrid);
        given(collectionUtils.getThumbnailsPaths(collection)).willReturn(thumbnailsPaths);

        collectionGrid.setCollection(collection);

        verify(imageGrid).setImages(thumbnailsPaths);
        verify(children).add(imageGrid);
    }
}