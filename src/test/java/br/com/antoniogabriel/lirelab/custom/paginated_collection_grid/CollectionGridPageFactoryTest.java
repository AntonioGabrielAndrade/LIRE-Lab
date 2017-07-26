package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
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

    @Mock private CollectionGrid page;
    @Mock private ImageClickHandler handler;
    @Mock private List<Image> images;
    @Mock private List<Image> imagesSubSet;

    @Test
    public void shouldReturnCollectionGridWithImagesSubsetForInformedPage() throws Exception {
        TestableCollectionGridPageFactory factory;
        int pageSize = 10;
        int totalImages = 95;

        factory = getFactory(pageSize, totalImages);

        factory.call(0);
        verify(images).subList(0, 10);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(1);
        verify(images).subList(10, 20);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(6);
        verify(images).subList(60, 70);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(9);
        verify(images).subList(90, 95);
        verify(page).setImages(imagesSubSet, handler);
    }

    private TestableCollectionGridPageFactory getFactory(int pageSize, int totalImages) {
        reset(page);

        given(images.subList(anyInt(), anyInt())).willReturn(imagesSubSet);
        given(images.size()).willReturn(totalImages);

        return new TestableCollectionGridPageFactory(images, pageSize, handler);
    }

    private class TestableCollectionGridPageFactory extends CollectionGridPageFactory {

        public TestableCollectionGridPageFactory(List<Image> images, int pageSize, ImageClickHandler handler) {
            super(images, pageSize, handler, null, null);
        }

        @Override
        protected CollectionGrid createCollectionGrid() {
            return page;
        }
    }
}