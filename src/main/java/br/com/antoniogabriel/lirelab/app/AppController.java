package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.search.SearchController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class AppController implements Initializable {

    @FXML private BorderPane mainArea;
    @FXML private Node searchView;
    @FXML private Node homeView;
    @FXML private ComboBox<Collection> collectionsComboBox;
    @FXML private HomeController homeViewController;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSearchComboBoxCollections();
        collectionService.addCollectionsChangeListener(() -> Platform.runLater(() -> setSearchComboBoxCollections()));

        ApplicationCommands commands = new ApplicationCommands(this);
        homeViewController.listenToCollectionsRightClick(commands.getCollectionCommands());
    }

    private void setSearchComboBoxCollections() {
        collectionsComboBox.getItems().clear();
        List<Collection> collections = collectionService.getCollections();
        collectionsComboBox.setItems(FXCollections.observableList(collections));
        collectionsComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void openCreateCollectionDialog(ActionEvent event) {
        openCreateCollectionDialog();
    }

    public void openCreateCollectionDialog() {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    @FXML
    public void searchCollection(ActionEvent event) {
        searchCollection(collectionsComboBox.getValue());
    }

    public void searchCollection(Collection collection) {
        mainArea.setCenter(searchView);
        collectionsComboBox.setValue(collection);
        searchController.startSearchSession(collection, collection.getFeatures().get(0));
    }

    @FXML
    public void showHomeView(ActionEvent event) {
        mainArea.setCenter(homeView);
    }

    @FXML
    public void openAboutDialog(ActionEvent event) {
        showAboutDialog();
    }

    public void showAboutDialog() {
        aboutFXML.loadOwnedBy(dialogProvider.getWindowFrom(mainArea));
    }

    public void deleteCollection(Collection collection) {
        collectionService.deleteCollection(collection);
    }
}
