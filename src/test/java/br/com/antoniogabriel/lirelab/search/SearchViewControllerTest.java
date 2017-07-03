package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
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

    @Mock private CollectionGrid collectionGrid;

    @InjectMocks private SearchViewController controller = new SearchViewController();

    @Test
    public void shouldShowCollectionAndAddImageToQueryPaneWhenClicked() throws Exception {
        Collection collection = new Collection();

        controller.startSearchSession(collection, CEDD);

        verify(collectionGrid).setCollection(eq(collection),
                any(SearchViewController.SetImageToGridClickHandler.class));
    }
}