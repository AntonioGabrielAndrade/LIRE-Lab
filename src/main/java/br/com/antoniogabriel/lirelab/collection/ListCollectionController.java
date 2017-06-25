package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.MainAreaController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    private MainAreaController mainAreaController;
    private Map<String, Collection> nameToCollectionMap = new HashMap<>();

    @Inject
    public ListCollectionController(CollectionService service, MainAreaController appController) {
        this.service = service;
        this.mainAreaController = appController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildCollectionsTree();
        listenToCollectionSelection();
        listenToCollectionsChange();
    }

    private void buildCollectionsTree() {
        collectionsTree.setRoot(getRootItemFor(service.getCollections()));
        collectionsTree.setShowRoot(false);
    }

    private void listenToCollectionSelection() {
        collectionsTree.getSelectionModel()
                .selectedItemProperty()
                .addListener(getTreeItemChangeListener());
    }

    protected ChangeListener<TreeItem<String>> getTreeItemChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (newValue != null) {
                String value = newValue.getValue();
                if (nameToCollectionMap.containsKey(value)) {
                    Collection collection = nameToCollectionMap.get(value);
                    mainAreaController.showCollectionImages(collection);
                }
            }
        };
    }

    private TreeItem<String> getRootItemFor(List<Collection> collections) {
        TreeItem root = createRootItem();
        root.setExpanded(true);

        for (Collection collection : collections) {
            root.getChildren().add(getItemFor(collection));
            nameToCollectionMap.put(collection.getName(), collection);
        }
        return root;
    }

    protected TreeItem createRootItem() {
        return new TreeItem();
    }

    private TreeItem<String> getItemFor(Collection collection) {
        TreeItem<String> item = createCollectionItem(collection);
        item.setGraphic(new FontIcon("fa-folder"));

        insertImageItens(item, collection);

        return item;
    }

    protected TreeItem<String> createCollectionItem(Collection collection) {
        return new TreeItem<>(collection.getName());
    }

    private void insertImageItens(TreeItem<String> item, Collection collection) {
        List<String> images = collection.getImagePaths();
        for (String image : images) {
            TreeItem<String> imageItem = new TreeItem<>(Paths.get(image).getFileName().toString());
            imageItem.setGraphic(new FontIcon("fa-file-image-o"));
            item.getChildren().add(imageItem);
        }
    }

    private void listenToCollectionsChange() {
//        service.addCollectionsChangeListener(() -> Platform.runLater(() -> buildCollectionsTree()));
        service.addCollectionsChangeListener(getCollectionsChangeListener());
    }

    protected Runnable getCollectionsChangeListener() {
        return new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> ListCollectionController.this.buildCollectionsTree());
            }
        };
    }
}
