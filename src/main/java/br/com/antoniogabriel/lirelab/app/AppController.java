package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.search.SearchController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class AppController implements Initializable {

    @FXML private BorderPane mainArea;
    @FXML private Node searchView;
    @FXML private Node homeView;
    @FXML private ComboBox<Collection> collectionsComboBox;

    private CreateCollectionFXML createCollectionFXML;
    private CollectionService collectionService;
    private AboutFXML aboutFXML;
    private DialogProvider dialogProvider;
    private HomeController homeController;
    private SearchController searchController;

    @Inject
    public AppController(CreateCollectionFXML createCollectionFXML,
                         CollectionService collectionService,
                         AboutFXML aboutFXML, DialogProvider dialogProvider,
                         SearchController searchController,
                         HomeController homeController) {

        this.createCollectionFXML = createCollectionFXML;
        this.collectionService = collectionService;
        this.aboutFXML = aboutFXML;
        this.dialogProvider = dialogProvider;
        this.searchController = searchController;
        this.homeController = homeController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSearchComboBoxCollections();
        collectionService.addCollectionsChangeListener(() -> Platform.runLater(() -> setSearchComboBoxCollections()));
    }

    private void setSearchComboBoxCollections() {
        collectionsComboBox.getItems().clear();
        List<Collection> collections = collectionService.getCollections();
        collectionsComboBox.setItems(FXCollections.observableList(collections));
        collectionsComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    @FXML
    public void searchCollection(ActionEvent event) {
        Collection selectedCollection = collectionsComboBox.getValue();
        Feature feature = chooseFeature(selectedCollection, dialogProvider.getWindowFrom(mainArea));

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
