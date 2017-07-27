package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.Slider;
import javafx.util.Callback;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid.PAGINATION_HIDDEN;
import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.Assert.assertTrue;
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
    @Mock private Slider gridGapSlider;
    @Mock private Slider imageHeightSlider;

    private ObservableList<String> paginationStyleClass = observableArrayList();

    @InjectMocks private PaginatedCollectionGrid grid = new PaginatedCollectionGrid();

    @Before
    public void setUp() throws Exception {
        given(pagination.getStyleClass()).willReturn(paginationStyleClass);
    }

    @Test
    public void shouldCalcPageCountToAccommodateAllImages() throws Exception {
        grid.setCollection(withAmountOfImages(100));

        grid.setPageSize(30);
        verify(pagination).setPageCount(4);

        grid.setPageSize(10);
        verify(pagination).setPageCount(10);

        grid.setPageSize(15);
        verify(pagination).setPageCount(7);

        grid.setPageSize(90);
        verify(pagination).setPageCount(2);
    }

    @Test
    public void shouldHidePaginationControlWhenHasOnlyOnePage() throws Exception {
        grid.setCollection(withAmountOfImages(10));
        grid.setPageSize(10);

        assertTrue(paginationStyleClass.contains(PAGINATION_HIDDEN));
    }

    @Test
    public void shouldSetPageFactory() throws Exception {
        int minPageSize = 1;
        List<Image> images = new ArrayList<>();

        given(pageFactoryProvider.getPageFactory(images, minPageSize, handler, null, null))
                .willReturn(pageFactory);

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