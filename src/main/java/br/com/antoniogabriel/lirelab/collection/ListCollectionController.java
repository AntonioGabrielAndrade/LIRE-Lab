package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.MainAreaController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ListCollectionController implements Initializable {
    @FXML
    private TreeView<String> collectionsTree;

    private CollectionService service;

    private Map<String, Collection> nameToCollectionMap = new HashMap<>();

    private MainAreaController mainAreaController;

    @Inject
    public ListCollectionController(CollectionService service, MainAreaController appController) {
        this.service = service;
        this.mainAreaController = appController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildCollectionsTree();
        listenToCollectionsChange();
    }

    private void buildCollectionsTree() {
        collectionsTree.setRoot(getRootItemFor(service.getCollections()));
        collectionsTree.setShowRoot(false);
        collectionsTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        String value = newValue.getValue();
                        if (nameToCollectionMap.containsKey(value)) {
                            Collection collection = nameToCollectionMap.get(value);
                            mainAreaController.showCollectionImages(collection);
                        }
                    }
                });
    }

    @NotNull
    private TreeItem<String> getRootItemFor(List<Collection> collections) {
        TreeItem root = new TreeItem();
        root.setExpanded(true);

        for (Collection collection : collections) {
            root.getChildren().add(getItemFor(collection));
            nameToCollectionMap.put(collection.getName(), collection);
        }
        return root;
    }

    private TreeItem<String> getItemFor(Collection collection) {
        TreeItem<String> item = new TreeItem<>(collection.getName());
        item.setGraphic(new FontIcon("fa-folder"));

        insertImageItens(item, collection);

        return item;
    }

    private void insertImageItens(TreeItem<String> item, Collection collection) {
        List<String> images = collection.getImagePaths();
        for (String image : images) {
            TreeItem<String> imageItem = new TreeItem<>(Paths.get(image).getFileName().toString());
            item.getChildren().add(imageItem);
        }
    }

    private void listenToCollectionsChange() {
        service.addCollectionsChangeListener(() -> Platform.runLater(() -> buildCollectionsTree()));
    }
}
