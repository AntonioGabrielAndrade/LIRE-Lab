package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

public class DialogProvider {

    public File chooseImagesDirectory(Window window) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the directory that contains the images");
        return chooser.showDialog(window);
    }

    public Window getWindowFrom(ActionEvent event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }

    public ProgressDialog getProgressDialog(Task<Void> task, Window window) {
        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(window);
        return dialog;
    }
}
