package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.search.SearchViewController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {

    @Mock private Window window;
    @Mock private CreateCollectionFXML createCollectionFXML;
    @Mock private ActionEvent event;
    @Mock private DialogProvider dialogProvider;
    @Mock private BorderPane mainArea;
    @Mock private Node searchView;
    @Mock private Node homeView;
    @Mock private SearchViewController searchViewController;
    @Mock private HomeViewController homeController;
    @Mock private HBox searchToolBar;

    @InjectMocks private AppController controller =
            new AppController(createCollectionFXML,
                                    dialogProvider,
                                    searchViewController,
                                    homeController);

    private Collection collection;

    @Before
    public void setUp() throws Exception {
        collection = new Collection();
        collection.setFeatures(asList(CEDD));
    }

    @Test
    public void shouldOpenCreateCollectionDialog() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);

        controller.openCreateCollectionDialog(event);

        verify(createCollectionFXML).loadOwnedBy(window);
    }

    @Test
    public void shouldShowSearchViewWhenSearchingCollection() throws Exception {
        given(homeController.getSelectedCollection()).willReturn(collection);

        controller.searchCollection(event);

        verify(mainArea).setCenter(searchView);
        verify(searchViewController).startSearchSession(collection, CEDD);
    }

    @Test
    public void shouldShowSearchToolbarWhenSearchingCollection() throws Exception {
        given(homeController.getSelectedCollection()).willReturn(collection);

        controller.searchCollection(event);

        verify(searchToolBar).setVisible(true);
    }

    @Test
    public void shouldLetUserChooseFeatureWhenSearchingCollectionWithMoreThanOneFeature() throws Exception {
        collection.setFeatures(asList(CEDD, TAMURA));
        given(homeController.getSelectedCollection()).willReturn(collection);
        given(dialogProvider.chooseFeatureFrom(collection)).willReturn(TAMURA);

        controller.searchCollection(event);

        verify(mainArea).setCenter(searchView);
        verify(searchViewController).startSearchSession(collection, TAMURA);
    }

    @Test
    public void shouldShowHomeView() throws Exception {
        controller.showHomeView(event);

        verify(searchToolBar).setVisible(false);
        verify(mainArea).setCenter(homeView);
    }
}