package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.search.SearchController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppController {

    @FXML private BorderPane mainArea;
    @FXML private Node searchView;
    @FXML private Node homeView;
    @FXML private ToolBarController toolBarController;

    private CreateCollectionFXML createCollectionFXML;
    private CollectionService collectionService;
    private AboutFXML aboutFXML;
    private DialogProvider dialogProvider;
    private SearchController searchController;

    @Inject
    public AppController(CreateCollectionFXML createCollectionFXML,
                         CollectionService collectionService,
                         AboutFXML aboutFXML, DialogProvider dialogProvider,
                         SearchController searchController) {

        this.createCollectionFXML = createCollectionFXML;
        this.collectionService = collectionService;
        this.aboutFXML = aboutFXML;
        this.dialogProvider = dialogProvider;
        this.searchController = searchController;
    }

    public void openCreateCollectionDialog() {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    public void searchCollection(Collection collection) {
        mainArea.setCenter(searchView);
        toolBarController.setSelectedCollection(collection);
        searchController.startSearchSession(collection, collection.getFeatures().get(0));
    }

    public void showHomeView() {
        mainArea.setCenter(homeView);
    }

    public void showAboutDialog() {
        aboutFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    public void deleteCollection(Collection collection) {
        if(dialogProvider.confirmDeleteCollection()) {
            collectionService.deleteCollection(collection);
        }
    }
}
