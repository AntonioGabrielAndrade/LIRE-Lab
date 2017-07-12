package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.util.Callback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PaginatedCollectionGridTest {

    public static final JFXPanel INIT_JAVAFX = new JFXPanel();

    public static final String SOME_PATH = "";

    @Mock private Pagination pagination;
    @Mock private ImageClickHandler handler;
    @Mock private Callback<Integer, Node> pageFactory;
    @Mock private PageFactoryProvider pageFactoryProvider;

    @InjectMocks private PaginatedCollectionGrid grid = new PaginatedCollectionGrid();

    @Test
    public void shouldCalcPageCountToAccommodateAllImages() throws Exception {
        grid.setPageSize(3);
        grid.setCollection(withAmountOfImages(10));
        verify(pagination).setPageCount(4);

        grid.setPageSize(10);
        grid.setCollection(withAmountOfImages(100));
        verify(pagination).setPageCount(10);

        grid.setPageSize(15);
        grid.setCollection(withAmountOfImages(14));
        verify(pagination).setPageCount(1);

        grid.setPageSize(12);
        grid.setCollection(withAmountOfImages(13));
        verify(pagination).setPageCount(2);
    }

    @Test
    public void shouldSetPageFactory() throws Exception {
        int pageSize = 12;
        List<Image> images = new ArrayList<>();

        given(pageFactoryProvider.getPageFactory(images, pageSize, handler))
                .willReturn(pageFactory);

        grid.setPageSize(pageSize);
        grid.setCollection(images, handler);

        verify(pagination).setPageFactory(pageFactory);
    }

    private Collection withAmountOfImages(int amount) {
        Collection collection = new Collection();
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            images.add(new Image(SOME_PATH, SOME_PATH));
        }
        collection.setImages(images);
        return collection;
    }
}