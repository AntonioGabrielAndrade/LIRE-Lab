package br.com.antoniogabriel.lirelab.collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.inject.Inject;
import java.net.URL;
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
        TreeItem root = new TreeItem();
        root.setExpanded(true);

        for (Collection collection :  service.getCollections()) {
            ViewableCollection vCollection  = getViewableCollection(collection);
            TreeItem<ViewableCollection> item = new TreeItem<>(vCollection);
            item.setGraphic(new FontIcon("fa-folder"));
            root.getChildren().add(item);
        }

        collectionsTree.setRoot(root);
        collectionsTree.setShowRoot(false);

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
