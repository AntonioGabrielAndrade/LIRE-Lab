package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.search.SearchViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class AppController {

    @FXML private BorderPane mainArea;
    @FXML private Node searchView;

    private CreateCollectionFXML createCollectionFXML;
    private DialogProvider dialogProvider;
    private HomeViewController homeViewController;
    private SearchViewController searchViewController;

    @Inject
    public AppController(CreateCollectionFXML createCollectionFXML,
                         DialogProvider dialogProvider,
                         SearchViewController searchViewController,
                         HomeViewController homeController) {

        this.createCollectionFXML = createCollectionFXML;
        this.dialogProvider = dialogProvider;
        this.searchViewController = searchViewController;
        this.homeViewController = homeController;
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(event));
    }

    public void searchCollection(ActionEvent event) {
        mainArea.setCenter(searchView);
        searchViewController.startSearchSession(homeViewController.getSelectedCollection());
    }
}
