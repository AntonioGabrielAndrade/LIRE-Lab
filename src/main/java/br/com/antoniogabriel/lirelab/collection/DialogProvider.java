package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.ChooseFeatureDialog;
import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import br.com.antoniogabriel.lirelab.custom.progress_dialog.ProgressDialog;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.*;
import static java.util.Arrays.asList;
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

    public Feature chooseFeatureFrom(Collection collection, Window owner) {
        List<Feature> features = collection.getFeatures();
        ChooseFeatureDialog dialog = new ChooseFeatureDialog(features);
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

    public boolean confirmDeleteCollection() {
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Delete Collection");
        alert.setHeaderText("Are you sure you want to delete collection?");
        alert.setContentText("This operation cannot be undone");

        return alert.showAndWait().get() == OK;
    }
}
