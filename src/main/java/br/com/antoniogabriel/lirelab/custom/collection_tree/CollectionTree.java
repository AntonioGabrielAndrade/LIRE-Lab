package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageSelectionListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTree extends StackPane {

    @FXML private TreeView<String> treeView;
    @FXML private TreeItem<String> rootItem;

    private TreeItemBuilder treeItemBuilder = new TreeItemBuilder();
    private List<CollectionSelectionListener> collectionListeners = new ArrayList<>();
    private List<ImageSelectionListener> imageListeners = new ArrayList<>();

    private Map<TreeItem<String>, Collection> collectionMap = new HashMap<>();
    private Map<TreeItem<String>, String> imageMap = new HashMap<>();

    public CollectionTree() {
        loadFXML();
        listenToCollectionAndImageSelection();
    }

    protected void listenToCollectionAndImageSelection() {
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

    protected void loadFXML() {
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

    private Collection getCollection(TreeItem<String> item) {
        return collectionMap.get(item);
    }

    private String getImage(TreeItem<String> item) {
        return imageMap.get(item);
    }

    private boolean isCollection(TreeItem<String> item) {
        return collectionMap.get(item) != null;
    }

    public void setCollections(List<Collection> collections) {
        addCollectionsToTree(collections);
    }

    public void selectCollection(int index) {
        treeView.getSelectionModel().select(index);
    }

    public void selectImage(int collectionIndex, int imageIndex) {
        TreeItem<String> item = treeView.getTreeItem(collectionIndex).getChildren().get(imageIndex);
        treeView.getSelectionModel().select(item);
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

        for (String image : imagesIn(collection)) {
            TreeItem<String> imageItem = createImageItem(image);
            imageMap.put(imageItem, image);
            addImageItemToCollectionItem(collectionItem, imageItem);
        }

        return collectionItem;
    }

    private List<String> imagesIn(Collection collection) {
        return collection.getImagePaths();
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

    public void addCollectionSelectionListener(CollectionSelectionListener listener) {
        collectionListeners.add(listener);
    }

    public void setItemBuilder(TreeItemBuilder itemBuilder) {
        this.treeItemBuilder = itemBuilder;
    }

    public void addImageSelectionListener(ImageSelectionListener listener) {
        this.imageListeners.add(listener);
    }

    public Collection getSelectedCollection() {
        return getCollection(treeView.getSelectionModel().getSelectedItem());
    }
}
