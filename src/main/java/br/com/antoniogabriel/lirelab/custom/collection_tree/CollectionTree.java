/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTree extends StackPane {

    @FXML private TreeView<String> treeView;
    @FXML private TreeItem<String> rootItem;

    private SimpleListProperty<Collection> collectionsProperty = new SimpleListProperty<>();
    private SimpleObjectProperty<Collection> selectedCollectionProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Image> selectedImageProperty = new SimpleObjectProperty<>();

    private TreeItemBuilder treeItemBuilder = new TreeItemBuilder();

    private Map<TreeItem<String>, Collection> collectionMap = new HashMap<>();
    private Map<TreeItem<String>, Image> imageMap = new HashMap<>();

    public CollectionTree() {
        loadFXML();
        listenToCollectionsListChange();
        listenToCollectionAndImageSelection();
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
                .addListener((observable, oldItem, newItem) -> {
                    if(isCollection(newItem)) {
                        selectedImageProperty.set(null);
                        selectedCollectionProperty.set(getCollection(newItem));
                    } else if(isImage(newItem)) {
                        selectedCollectionProperty.set(null);
                        selectedImageProperty.set(getImage(newItem));
                    }
                });
    }

    public Collection getSelectedCollection() {
        return selectedCollectionProperty.get();
    }

    public SimpleObjectProperty<Collection> selectedCollectionProperty() {
        return selectedCollectionProperty;
    }

    public Image getSelectedImage() {
        return selectedImageProperty.get();
    }

    public SimpleObjectProperty<Image> selectedImageProperty() {
        return selectedImageProperty;
    }

    public ObservableList<Collection> getCollections() {
        return collectionsProperty.get();
    }

    public SimpleListProperty<Collection> collectionsProperty() {
        return collectionsProperty;
    }

    public void setCollections(List<Collection> collections) {
        this.collectionsProperty.setValue(FXCollections.observableArrayList(collections));
    }

    public void bindCollectionsTo(ListProperty<Collection> property) {
        collectionsProperty().bind(property);
    }

    public void bindVisibilityTo(ObservableBooleanValue value) {
        visibleProperty().bind(value);
    }

    public void selectImage(int collectionIndex, int imageIndex) {
        TreeItem<String> item = treeView.getTreeItem(collectionIndex).getChildren().get(imageIndex);
        treeView.getSelectionModel().select(item);
    }

    public void selectCollection(int index) {
        treeView.getSelectionModel().select(index);
    }

    public void setItemBuilder(TreeItemBuilder itemBuilder) {
        this.treeItemBuilder = itemBuilder;
    }

    private Collection getCollection(TreeItem<String> item) {
        return collectionMap.get(item);
    }

    private Image getImage(TreeItem<String> item) {
        return imageMap.get(item);
    }

    private boolean isCollection(TreeItem<String> item) {
        return collectionMap.get(item) != null;
    }

    private boolean isImage(TreeItem<String> item) {
        return imageMap.get(item) != null;
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
            imageMap.put(imageItem, image);
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
