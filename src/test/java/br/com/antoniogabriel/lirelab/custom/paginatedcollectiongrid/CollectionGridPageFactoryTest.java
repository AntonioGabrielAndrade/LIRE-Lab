package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridPageFactoryTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private List<Image> images;
    @Mock private CollectionGrid grid;
    @Mock private ImageClickHandler handler;

    @Test
    public void shouldReturnCollectionGridWithImagesRangeForInformedPage() throws Exception {
        TestableCollectionGridPageFactory factory;
        int pageSize = 10;
        int totalImages = 100;

        factory = getFactory(pageSize, totalImages);

        factory.call(0);
        verify(images).subList(0, 10);
        verify(grid).setImages(images, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(1);
        verify(images).subList(10, 20);
        verify(grid).setImages(images, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(2);
        verify(images).subList(20, 30);
        verify(grid).setImages(images, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(3);
        verify(images).subList(30, 40);
        verify(grid).setImages(images, handler);
    }

    private TestableCollectionGridPageFactory getFactory(int pageSize, int totalImages) {
        reset(grid);

        given(images.size()).willReturn(totalImages);
        given(images.subList(anyInt(), anyInt())).willReturn(images);

        Collection collection = new Collection();
        collection.setImages(images);

        return new TestableCollectionGridPageFactory(collection, pageSize, handler);
    }

    private class TestableCollectionGridPageFactory extends CollectionGridPageFactory {

        public TestableCollectionGridPageFactory(Collection collection, int pageSize, ImageClickHandler handler) {
            super(collection, pageSize, handler);
        }

        @Override
        protected CollectionGrid createCollectionGrid() {
            return grid;
        }
    }
}