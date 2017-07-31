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

package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.FeatureSelectionListener;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class SearchOutput extends BorderPane {

    private static final String SEARCH_OUTPUT_FXML = "search-output.fxml";

    @FXML private PaginatedCollectionGrid outputGrid;
    @FXML private StatusBar statusBar;
    @FXML private HBox titleGraphics;

    public SearchOutput() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SEARCH_OUTPUT_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addTitleGraphics(Node node) {
        titleGraphics.getChildren().add(node);
    }

    public void bindProgressTo(Task<?> task, String message) {
        statusBar.bindProgressTo(task, message);
    }

    public void setCollection(Collection collection) throws IOException {
        outputGrid.setCollection(collection);
    }

    public void setCollection(Collection collection, ImageClickHandler handler) {
        outputGrid.setCollection(collection, handler);
    }

    public void setCollection(List<Image> images, ImageClickHandler handler) {
        outputGrid.setCollection(images, handler);
    }

    public void setMessage(String message) {
        statusBar.setMessage(message);
    }

    public Feature getSelectedFeature() {
        return statusBar.getSelectedFeature();
    }

    public void clear() {
        statusBar.clear();
    }

    public void setFeatures(List<Feature> features, Feature defaultFeature, FeatureSelectionListener listener) {
        statusBar.setFeatures(features, defaultFeature, listener);
    }

    public void disableTitleGraphics() {
        titleGraphics.setDisable(true);
    }

    public void enableTitleGraphics() {
        titleGraphics.setDisable(false);
    }
}
