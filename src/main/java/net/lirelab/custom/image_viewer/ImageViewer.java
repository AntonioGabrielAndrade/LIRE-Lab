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

package net.lirelab.custom.image_viewer;

import javafx.scene.control.ProgressIndicator;
import net.lirelab.app.ImageViewFactory;
import net.lirelab.collection.Image;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import static net.lirelab.util.FxUtils.runOnFxThreadAndWait;

public class ImageViewer extends BorderPane {

    public static final String IMAGE_VIEWER_FXML = "image-viewer.fxml";

    @FXML private BorderPane root;
    @FXML private Slider imageHeightSlider;

    private ProgressIndicator progressIndicator = new ProgressIndicator();

    private ImageViewFactory imageViewFactory = new ImageViewFactory();

    public ImageViewer(Image image) {
        loadFXML();

        progressIndicator.setMaxHeight(50.0);

        ImageView imageView = imageViewFactory.create(image.getImagePath(), true);
        imageView.getImage().progressProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() == 1.0) {
                runOnFxThreadAndWait(() -> setImage(imageView));
            }
        });

        imageView.fitHeightProperty().bind(imageHeightSlider.valueProperty());

        root.setCenter(progressIndicator);
    }

    private void setImage(ImageView imageView) {
        if(imageView.getImage().getProgress() == 1.0) {
            root.setCenter(imageView);
        }
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(IMAGE_VIEWER_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
