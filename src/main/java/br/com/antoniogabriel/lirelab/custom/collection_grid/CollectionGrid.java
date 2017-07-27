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

package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private Collection collection;

    @FXML private ImageGrid grid;

    private EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();
    private ToolTipProvider toolTipProvider = new ToolTipProvider();

    public CollectionGrid() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COLLECTION_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setCollection(Collection collection) throws IOException {
        setCollection(collection, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setCollection(Collection collection, ImageClickHandler handler) throws IOException {
        this.collection = collection;
        setImages(collection.getImages(), handler);
    }

    public void setImages(List<Image> images) throws IOException {
        setImages(images, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setImages(List<Image> images, ImageClickHandler handler) throws IOException {
        grid.clear();
        for (Image image : images) {
            ImageView imageView = grid.addImage(image.getThumbnailPath());

            if(imageView == null)
                continue;

//            toolTipProvider.setToolTip(imageView, image);
            toolTipProvider.setPopOver(imageView, image);

            imageView.setOnMouseClicked(eventHandlerFactory.createFrom(image, handler));

            imageView.setOnMouseEntered(event -> getScene().setCursor(Cursor.HAND));
            imageView.setOnMouseExited(event -> getScene().setCursor(Cursor.DEFAULT));

        }
    }

    public Collection getCollection() {
        return collection;
    }

    public void bindGapsTo(DoubleProperty property) {
        grid.bindGapsTo(property);
    }

    public void bindImageHeightTo(DoubleProperty property) {
        grid.bindImagesHeightTo(property);
    }
}
