package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.NewChooseFeatureDialog;
import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import br.com.antoniogabriel.lirelab.custom.progress_dialog.ProgressDialog;
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

    public Feature chooseFeatureFrom(Collection collection, Window owner) {
        List<Feature> features = collection.getFeatures();
        NewChooseFeatureDialog dialog = new NewChooseFeatureDialog(features);
        dialog.initOwner(owner);
        return dialog.showAndGetFeature();
    }

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> {
            Collection collection = new Collection("Collection");
            collection.setFeatures(asList(CEDD, TAMURA, FCTH, COLOR_HISTOGRAM));
            Feature feature = new DialogProvider().chooseFeatureFrom(collection, null);
            System.out.println("selected feature: " + feature);
        });

    }
}
