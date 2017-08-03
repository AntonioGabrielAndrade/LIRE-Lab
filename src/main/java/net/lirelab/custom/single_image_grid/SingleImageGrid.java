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

package net.lirelab.custom.single_image_grid;

import net.lirelab.collection.Image;
import net.lirelab.custom.image_grid.ImageGrid;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SingleImageGrid extends StackPane {

    public static final String SINGLE_IMAGE_GRID_FXML = "single-image-grid.fxml";

    @FXML private ImageGrid imageGrid;

    private Image image;
    private List<ImageChangeListener> listeners = new ArrayList<>();

    public SingleImageGrid() {
        loadFXML();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SINGLE_IMAGE_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void setImageHeight(int height) {
        imageGrid.setImagesHeight(height);
    }

    public void setImage(Image image) {
        this.image = image;
        imageGrid.clear();
        imageGrid.addImage(getBestPath(image));
        executeListeners(image);
    }

    private String getBestPath(Image image) {
        return image.getThumbnailPath().isEmpty() ?
                image.getImagePath() :
                image.getThumbnailPath();
    }

    private void executeListeners(Image image) {
        for (ImageChangeListener listener : listeners) {
            listener.changed(image);
        }
    }

    public Image getImage() {
        return image;
    }

    public void setOnChange(ImageChangeListener listener) {
        listeners.add(listener);
    }

    public void clear() {
        imageGrid.clear();
        listeners.clear();
        image = null;
    }

    public void removeAllListenersButFirst() {
        ImageChangeListener first = listeners.get(0);
        listeners.clear();
        listeners.add(first);
    }
}
