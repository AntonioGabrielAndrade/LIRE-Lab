package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.imagedialog.ImageDialog;
import br.com.antoniogabriel.lirelab.custom.progressdialog.ProgressDialog;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;

public class DialogProvider {

    public File chooseImagesDirectory(Window window) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the directory that contains the images");
        return chooser.showDialog(window);
    }

    public Window getWindowFrom(Event event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }

    public ProgressDialog getProgressDialog(Task<Void> task, Window window) {
        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(window);
        return dialog;
    }

    public ImageDialog getImageDialog(String imagePath, Window window) throws FileNotFoundException {
        ImageDialog dialog = new ImageDialog(imagePath);
        dialog.initOwner(window);
        return dialog;
    }
}
