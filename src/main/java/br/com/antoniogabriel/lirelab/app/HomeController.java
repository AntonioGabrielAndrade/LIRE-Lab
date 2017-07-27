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

package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionContextMenuFactory;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.custom.collection_detail.CollectionDetail;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionTree;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static br.com.antoniogabriel.lirelab.app.ApplicationCommands.CollectionCommand.SEARCH;
import static java.util.Arrays.asList;

@Singleton
public class HomeController implements Initializable {

    public static final int DEFAULT_COLLECTION_PAGE_SIZE = 120;

    private SimpleListProperty<Collection> collections = new SimpleListProperty<>();

    private CollectionService collectionService;

    private ImageViewFactory viewFactory;
    private ApplicationCommands applicationCommands;
    private ImageViewConfig viewConfig;

    @FXML private BorderPane centerPane;
    @FXML private CollectionTree collectionTree;
    @FXML private StackPane welcomeView;


    @Inject
    public HomeController(CollectionService collectionService,
                          ImageViewFactory viewFactory,
                          ApplicationCommands applicationCommands,
                          ImageViewConfig viewConfig) {

        this.collectionService = collectionService;
        this.viewFactory = viewFactory;
        this.applicationCommands = applicationCommands;
        this.viewConfig = viewConfig;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listenToCollectionSelection();
        listenToImageSelection();
        listenToCollectionsChange();
        bindCollectionsListToUI();
        loadCollections();
        setCollectionTreeContextMenu();
    }

    private void listenToCollectionSelection() {
        collectionTree.selectedCollectionProperty().addListener((observable, oldCollection, newCollection) -> showCollectionImages(newCollection));

        collectionTree.selectedCollectionProperty().addListener((observable, oldCollection, newCollection) -> {
            CollectionContextMenuFactory factory = new CollectionContextMenuFactory(applicationCommands.getCollectionCommands());
            collectionTree.setContextMenu(factory.createContextMenu(newCollection));
        });

        collectionTree.collectionsProperty().emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
            if(isEmpty) {
                setCollectionTreeContextMenu();
            }
        });
    }

    private void setCollectionTreeContextMenu() {
        if(collectionTree.getCollections().isEmpty()) {
            CollectionTreeContextMenuFactory factory = new CollectionTreeContextMenuFactory(applicationCommands.getCollectionTreeContextMenuCommands());
            collectionTree.setContextMenu(factory.createContextMenu());
        }
    }

    private void listenToCollectionsChange() {
        collectionService.addCollectionsChangeListener(() -> Platform.runLater(() -> loadCollections()));
    }

    private void listenToImageSelection() {
        collectionTree.selectedImageProperty().addListener((observable, oldImage, newImage) -> {
            if(newImage != null) {
                showImage(newImage);
            }
        });
    }

    private void bindCollectionsListToUI() {
        collectionTree.bindCollectionsTo(this.collections);
        welcomeView.visibleProperty().bind(this.collections.emptyProperty());

        this.collections.emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
            if(isEmpty) {
                centerPane.setCenter(welcomeView);
            } else {
                collectionTree.selectCollection(0);
            }
        });
    }

    private void loadCollections() {
        List<Collection> collections = collectionService.getCollections();
        this.collections.setValue(FXCollections.observableArrayList(collections));
        collectionTree.selectCollection(0);
    }

    public void showCollectionImages(Collection collection) {
        centerPane.setCenter(new CollectionDetail(collection, asList(applicationCommands.getCollectionCommand(SEARCH))));
    }

    public void showImage(String imagePath) {
        ImageView image = viewFactory.create(imagePath);
        viewConfig.bindImageHeight(image, centerPane, 0.8);
        centerPane.setCenter(image);
    }

    public Collection getSelectedCollection() {
        return collectionTree.getSelectedCollection();
    }
}
