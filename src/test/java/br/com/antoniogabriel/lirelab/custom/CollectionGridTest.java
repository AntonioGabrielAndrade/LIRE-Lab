package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static java.util.Collections.EMPTY_LIST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    @Mock private ImageGrid imageGrid;
    @Mock private ImageGridBuilder imageGridBuilder;
    @Mock private StackPane root;
    @Mock private ObservableList<Node> children;

    @InjectMocks private CollectionGrid collectionGrid = new TestableCollectionGrid();

    private PathResolver resolver = new PathResolver(TEST_ROOT);
    private List<String> thumbnailsPaths = EMPTY_LIST;
    private Collection collection;

    @Test
    public void shouldAddCollectionThumbnailsToGrid() throws Exception {
        given(root.getChildren()).willReturn(children);
        given(imageGridBuilder.build()).willReturn(imageGrid);

        collectionGrid.setCollection(collection);

        verify(imageGrid).setImages(thumbnailsPaths);
        verify(children).add(imageGrid);
    }

    private class TestableCollectionGrid extends CollectionGrid {
        public TestableCollectionGrid() {
            super(resolver, imageGridBuilder);
        }

        @Override
        protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
            return thumbnailsPaths;
        }
    }
}