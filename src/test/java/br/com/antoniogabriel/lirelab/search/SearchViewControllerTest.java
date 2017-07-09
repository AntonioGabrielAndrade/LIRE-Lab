package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SearchViewControllerTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private PaginatedCollectionGrid collectionGrid;
    @Mock private SingleImageGrid queryGrid;
    @Mock private CollectionService service;

    @InjectMocks private SearchViewController controller = new SearchViewController(service);

    @Test
    public void shouldShowCollectionAndAddImageToQueryPaneWhenClicked() throws Exception {
        Collection collection = new Collection();

        controller.startSearchSession(collection, CEDD);

        verify(collectionGrid).setCollection(eq(collection),
                any(SearchViewController.SetImageToGridClickHandler.class));
    }

    @Test
    public void shouldSetQueryChangeListenerToQueryGrid() throws Exception {
        Collection collection = new Collection();

        controller.startSearchSession(collection, CEDD);

        verify(queryGrid).setOnChange(any(SearchViewController.ImageChangeListenerImpl.class));
    }
}