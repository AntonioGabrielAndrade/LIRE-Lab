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

package net.lirelab.custom.collection_detail;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import net.lirelab.app.Command;
import net.lirelab.app.CommandTriggerFactory;
import net.lirelab.app.HomeController;
import net.lirelab.app.LireLabException;
import net.lirelab.collection.Collection;
import net.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import net.lirelab.lire.Feature;

import java.io.IOException;
import java.util.List;

public class CollectionDetail extends BorderPane {

    private static final String COLLECTION_DETAIL_FXML = "collection-detail.fxml";

    @FXML private BorderPane center;
    @FXML private TextField nameField;
    @FXML private TextField totalField;
    @FXML private TextField descriptionField;
    @FXML private ListView<Feature> featuresField;
    @FXML private StackPane topLine;

    private CommandTriggerFactory<Collection> commandTriggerFactory = new CommandTriggerFactory<>();

    public CollectionDetail(Collection collection) {
        loadFXML();
        show(collection);
    }

    public CollectionDetail(Collection collection, List<Command<Collection>> commands) {
        this(collection);
        setupToolBar(collection, commands);
    }

    private void setupToolBar(Collection collection, List<Command<Collection>> commands) {
        for (Command<Collection> command : commands) {
            Button button = commandTriggerFactory.createButton(command, () -> collection);
            button.setId("collection-detail-" + command.getNodeId());
            topLine.getChildren().add(button);
        }
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COLLECTION_DETAIL_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void show(Collection collection) {
        if(collection == null) return;

        showInfoOnTop(collection);
        showGridOnCenter(collection);
    }

    private void showInfoOnTop(Collection collection) {
        nameField.setText(collection.getName());
        totalField.setText(collection.totalImages() + "");
        descriptionField.setText(collection.getDescription());

        featuresField.setItems(FXCollections.observableArrayList(collection.getFeatures()));

        if(collection.getFeatures().size() > 4) {
            featuresField.setMinHeight(100);
        } else {
            featuresField.setMinHeight(50);
        }

    }

    private void showGridOnCenter(Collection collection) {
        try {

            PaginatedCollectionGrid grid = new PaginatedCollectionGrid();
            grid.setPageSize(HomeController.DEFAULT_COLLECTION_PAGE_SIZE);
            center.setCenter(grid);
            grid.setCollection(collection);

        } catch (IOException e) {
            throw new LireLabException("Could not show collections", e);
        }
    }
}
