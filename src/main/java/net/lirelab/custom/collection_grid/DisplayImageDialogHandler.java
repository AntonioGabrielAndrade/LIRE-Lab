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

package net.lirelab.custom.collection_grid;

import net.lirelab.collection.DialogProvider;
import net.lirelab.collection.Image;
import net.lirelab.custom.image_dialog.ImageDialog;
import net.lirelab.app.LireLabException;
import net.lirelab.util.FileUtils;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.FileNotFoundException;

public class DisplayImageDialogHandler implements ImageClickHandler {

    private final DialogProvider dialogProvider;
    private final FileUtils fileUtils;

    public DisplayImageDialogHandler(DialogProvider dialogProvider, FileUtils fileUtils) {
        this.dialogProvider = dialogProvider;
        this.fileUtils = fileUtils;
    }

    @Override
    public void handle(Image image, MouseEvent event) {
        try {

            String pathToShow = getImagePathToShow(image);

            Window window = dialogProvider.getWindowFrom(event);
            ImageDialog dialog = dialogProvider.getImageDialog(pathToShow, window);
            dialog.show();

        } catch (FileNotFoundException e) {
            throw new LireLabException("Error displaying image", e);
        }
    }

    private String getImagePathToShow(Image image) {
        return fileUtils.fileExists(image.getImagePath()) ?
                image.getImagePath() :
                image.getThumbnailPath();
    }
}
