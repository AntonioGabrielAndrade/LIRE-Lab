package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.search.SearchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class AppController {

    @FXML private BorderPane mainArea;
    @FXML private Node searchView;
    @FXML private Node homeView;

    private CreateCollectionFXML createCollectionFXML;
    private AboutFXML aboutFXML;
    private DialogProvider dialogProvider;
    private HomeController homeController;
    private SearchController searchController;

    @Inject
    public AppController(CreateCollectionFXML createCollectionFXML,
                         AboutFXML aboutFXML, DialogProvider dialogProvider,
                         SearchController searchController,
                         HomeController homeController) {

        this.createCollectionFXML = createCollectionFXML;
        this.aboutFXML = aboutFXML;
        this.dialogProvider = dialogProvider;
        this.searchController = searchController;
        this.homeController = homeController;
    }

    @FXML
    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    @FXML
    public void searchCollection(ActionEvent event) {
        Collection selectedCollection = homeController.getSelectedCollection();
        Feature feature = chooseFeature(selectedCollection, dialogProvider.getWindowFrom(event));

        mainArea.setCenter(searchView);
        searchController.startSearchSession(selectedCollection, feature);
    }

    @FXML
    public void showHomeView(ActionEvent event) {
        mainArea.setCenter(homeView);
    }

    @FXML
    public void openAboutDialog(ActionEvent event) {
        aboutFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    private Feature chooseFeature(Collection collection, Window window) {
        return hasMoreThanOneFeature(collection) ?
                dialogProvider.chooseFeatureFrom(collection, window) :
                firstFeatureOf(collection);
    }

    private Feature firstFeatureOf(Collection collection) {
        return collection.getFeatures().get(0);
    }

    private boolean hasMoreThanOneFeature(Collection collection) {
        return collection.getFeatures().size() > 1;
    }

}
