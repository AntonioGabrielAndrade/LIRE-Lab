package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.ChooseFeatureDialog;
import br.com.antoniogabriel.lirelab.custom.imagedialog.ImageDialog;
import br.com.antoniogabriel.lirelab.custom.progressdialog.ProgressDialog;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.*;
import static java.util.Arrays.asList;

public class DialogProvider {

    public File chooseImagesDirectory(Window window) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the directory that contains the images");
        return chooser.showDialog(window);
    }

    public Window getWindowFrom(Event event) {
        return ((Node) event.getSource()).getScene().getWindow();
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

    public Feature chooseFeatureFrom(Collection collection) {
        List<Feature> features = collection.getFeatures();
        ChooseFeatureDialog dialog = new ChooseFeatureDialog(features);
        return dialog.showAndGetFeature();
    }

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> {
            Collection collection = new Collection("Collection");
            collection.setFeatures(asList(CEDD, TAMURA, FCTH, COLOR_HISTOGRAM));
            Feature feature = new DialogProvider().chooseFeatureFrom(collection);
            System.out.println("selected feature: " + feature);
        });

    }
}
