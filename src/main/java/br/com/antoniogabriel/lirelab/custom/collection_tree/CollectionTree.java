package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageSelectionListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTree extends StackPane {

    @FXML private TreeView<String> treeView;
    @FXML private TreeItem<String> rootItem;

    private SimpleListProperty<Collection> collectionsProperty = new SimpleListProperty<>();

    private TreeItemBuilder treeItemBuilder = new TreeItemBuilder();
    private List<CollectionSelectionListener> collectionListeners = new ArrayList<>();
    private List<CollectionRightClickListener> collectionClickListeners = new ArrayList<>();
    private List<ImageSelectionListener> imageListeners = new ArrayList<>();

    private Map<TreeItem<String>, Collection> collectionMap = new HashMap<>();
    private Map<TreeItem<String>, String> imageMap = new HashMap<>();

    public CollectionTree() {
        loadFXML();
        listenToCollectionsListChange();
        listenToCollectionAndImageSelection();
        listenToCollectionRightClick();
    }

    public void setContextMenu(ContextMenu contextMenu) {
        treeView.setContextMenu(contextMenu);
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("collection-tree.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenToCollectionsListChange() {
        collectionsProperty.addListener((ListChangeListener<Collection>) c -> addCollectionsToTree(collectionsProperty));
    }

    private void listenToCollectionAndImageSelection() {
        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(isCollection(newValue)) {
                        for (CollectionSelectionListener listener : collectionListeners) {
                            listener.selected(getCollection(newValue));
                        }
                    } else {
                        for (ImageSelectionListener listener : imageListeners) {
                            listener.selected(getImage(newValue));
                        }
                    }
                });
    }

    private void listenToCollectionRightClick() {
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY && getSelectedCollection() != null) {

                Bounds boundsInScene = null;
                Node itemNode = treeView.getSelectionModel().getSelectedItem().getGraphic().getParent();

                if(itemNode != null) {
                    boundsInScene = itemNode.localToScene(itemNode.getBoundsInLocal());
                }

                for (CollectionRightClickListener listener : collectionClickListeners) {
                    listener.clicked(getSelectedCollection(), event, boundsInScene, itemNode);
                }
            }
        });
    }

    public void setCollections(List<Collection> collections) {
        this.collectionsProperty.setValue(FXCollections.observableArrayList(collections));
    }

    public SimpleListProperty<Collection> collectionsProperty() {
        return collectionsProperty;
    }

    public void bindCollectionsTo(ListProperty<Collection> property) {
        collectionsProperty().bind(property);
    }

    public void bindVisibilityTo(ObservableBooleanValue value) {
        visibleProperty().bind(value);
    }

    public Collection getSelectedCollection() {
        return getCollection(treeView.getSelectionModel().getSelectedItem());
    }

    public void selectImage(int collectionIndex, int imageIndex) {
        TreeItem<String> item = treeView.getTreeItem(collectionIndex).getChildren().get(imageIndex);
        treeView.getSelectionModel().select(item);
    }

    public void selectCollection(int index) {
        treeView.getSelectionModel().select(index);
    }

    public void addCollectionSelectionListener(CollectionSelectionListener listener) {
        collectionListeners.add(listener);
    }

    public void addCollectionRightClickListener(CollectionRightClickListener listener) {
        collectionClickListeners.add(listener);
    }

    public void addImageSelectionListener(ImageSelectionListener listener) {
        this.imageListeners.add(listener);
    }

    public void setItemBuilder(TreeItemBuilder itemBuilder) {
        this.treeItemBuilder = itemBuilder;
    }

    private Collection getCollection(TreeItem<String> item) {
        return collectionMap.get(item);
    }

    private String getImage(TreeItem<String> item) {
        return imageMap.get(item);
    }

    private boolean isCollection(TreeItem<String> item) {
        return collectionMap.get(item) != null;
    }

    private void addCollectionsToTree(List<Collection> collections) {
        clearTree();
        for (Collection collection : collections) {
            addCollectionItem(collection);
        }
    }

    private void addCollectionItem(Collection collection) {
        TreeItem<String> item = buildCollectionItem(collection);
        collectionMap.put(item, collection);
        rootItem.getChildren().add(item);
    }

    private TreeItem<String> buildCollectionItem(Collection collection) {
        TreeItem<String> collectionItem = createCollectionItem(collection);

        for (Image image : collection.getImages()) {
            TreeItem<String> imageItem = createImageItem(image.getImagePath());
            imageMap.put(imageItem, image.getImagePath());
            addImageItemToCollectionItem(collectionItem, imageItem);
        }

        return collectionItem;
    }

    private boolean addImageItemToCollectionItem(TreeItem<String> collectionItem, TreeItem<String> imageItem) {
        return collectionItem.getChildren().add(imageItem);
    }

    private TreeItem<String> createImageItem(String imagePath) {
        return treeItemBuilder.createItem(imagePath);
    }

    private TreeItem<String> createCollectionItem(Collection collection) {
        return treeItemBuilder.createItem(collection);
    }

    private void clearTree() {
        rootItem.getChildren().clear();
    }
}
