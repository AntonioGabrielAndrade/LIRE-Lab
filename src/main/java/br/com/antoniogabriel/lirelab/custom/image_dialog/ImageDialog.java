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

package br.com.antoniogabriel.lirelab.custom.image_dialog;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class ImageDialog extends Dialog<Void> {

    public static final double MAX_IMAGE_HEIGHT = 700;
    private final BorderPane contentRoot;

    private ImageViewFactory imageViewFactory;
    private DialogActions dialogActions;
    private ImageViewConfig imageViewConfig;

    public ImageDialog(String imagePath) {
        this(imagePath, new ImageViewFactory(), new ImageViewConfig(), null, new BorderPane());
    }

    public ImageDialog(String imagePath,
                       ImageViewFactory imageViewFactory,
                       ImageViewConfig imageViewConfig,
                       DialogActions dialogActions,
                       BorderPane contentRoot) {

        this.contentRoot = contentRoot;

        setDialogActions(dialogActions);
        setImageViewFactory(imageViewFactory);
        setImageViewConfig(imageViewConfig);
        setImageAsContent(imagePath);
        setDialogTitle(imagePath);
        setAsResizable();
        addOkButton();
        setDialogPaneId();

    }


    private void setDialogActions(DialogActions dialogActions) {
        this.dialogActions = dialogActions == null ?
                new DialogActions(this) :
                dialogActions;
    }

    private void setImageViewFactory(ImageViewFactory imageViewFactory) {
        this.imageViewFactory = imageViewFactory;
    }

    private void setImageAsContent(String imagePath) {
        ImageView imageView = this.imageViewFactory.create(imagePath, false);
        imageViewConfig.limitImageHeight(imageView, MAX_IMAGE_HEIGHT);
        contentRoot.setCenter(imageView);

        dialogActions.setContent(contentRoot);
    }

    private void setDialogTitle(String title) {
        dialogActions.setTitle(title);
    }

    private void setAsResizable() {
        dialogActions.setResizable(true);
    }

    private void addOkButton() {
        this.dialogActions.addButtonType(ButtonType.OK);
    }

    private void setDialogPaneId() {
        this.dialogActions.setDialogPaneId("image-dialog");
    }

    public void setImageViewConfig(ImageViewConfig imageViewConfig) {
        this.imageViewConfig = imageViewConfig;
    }
}
