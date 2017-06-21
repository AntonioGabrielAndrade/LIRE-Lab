package br.com.antoniogabriel.lirelab.collection;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListCollectionController implements Initializable {
    @FXML
    private TreeView<ViewableCollection> collectionsTree;

    private CollectionService service;

    @Inject
    public ListCollectionController(CollectionService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildCollectionTree();
        listenToCollectionsChange();
    }

    private void buildCollectionTree() {
        collectionsTree.setRoot(getRootItemFor(service.getCollections()));
        collectionsTree.setShowRoot(false);
    }

    @NotNull
    private TreeItem getRootItemFor(List<Collection> collections) {
        TreeItem root = new TreeItem();
        root.setExpanded(true);

        for (Collection collection : collections) {
            root.getChildren().add(getItemFor(collection));
        }
        return root;
    }

    private TreeItem<ViewableCollection> getItemFor(Collection collection) {
        ViewableCollection vCollection = getViewableCollection(collection);
        TreeItem<ViewableCollection> item = new TreeItem<>(vCollection);
        item.setGraphic(new FontIcon("fa-folder"));
        return item;
    }

    private void listenToCollectionsChange() {
        service.addCollectionsChangeListener(() -> Platform.runLater(() -> buildCollectionTree()));
    }

    private ViewableCollection getViewableCollection(Collection collection) {
        return new ViewableCollection(collection);
    }

    private class ViewableCollection {
        private final SimpleStringProperty name;
        private final Collection collection;

        public ViewableCollection(Collection collection) {
            this.name = new SimpleStringProperty(collection.getName());
            this.collection = collection;
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        @Override
        public String toString() {
            return name.get();
        }
    }
}
