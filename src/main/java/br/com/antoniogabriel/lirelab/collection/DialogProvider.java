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

package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import br.com.antoniogabriel.lirelab.custom.progress_dialog.ProgressDialog;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.ButtonType.OK;

public class DialogProvider {

    public File chooseImagesDirectory(Window window) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the directory that contains the images");
        return chooser.showDialog(window);
    }

    public File chooseImageFile(Window window) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select a image to query the collection");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Images", "jpg", "jpeg", "JPEG", "png"));
        return chooser.showOpenDialog(window);
    }

    public Window getWindowFrom(Event event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }

    public Window getWindowFrom(Node node) {
        return node.getScene().getWindow();
    }

    public ProgressDialog getProgressDialog(Task<Void> task, Window owner) {
        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(owner);
        return dialog;
    }

    public ImageDialog getImageDialog(String imagePath, Window owner) throws FileNotFoundException {
        ImageDialog dialog = new ImageDialog(imagePath);
        dialog.initOwner(owner);
        return dialog;
    }

    public boolean confirmDeleteCollection() {
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Delete Collection");
        alert.setHeaderText("Are you sure you want to delete collection?");
        alert.setContentText("This operation cannot be undone");

        return alert.showAndWait().get() == OK;
    }
}
