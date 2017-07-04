package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.singleimagegrid.SingleImageGrid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SearchViewControllerTest {

    @Mock private CollectionGrid collectionGrid;
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

    @Test
    public void shouldRunQueryWhenQueryImageIsSet() throws Exception {
        Collection collection = new Collection();
        collection.setImages(asList(new Image("path1", "path1"),
                                    new Image("path2", "path2")));

        Image queryImage = new Image("", "");

        SearchViewController.ImageChangeListenerImpl listener =
                new SearchViewController.ImageChangeListenerImpl(collection, CEDD, service, collectionGrid, queryGrid);

        listener.changed(queryImage);

        verify(service).runQuery(collection, CEDD, queryImage);
    }
}