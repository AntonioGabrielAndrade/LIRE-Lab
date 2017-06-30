package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;


public class ImageDialog extends Dialog<Void> {

    private final ImageViewFactory imageViewFactory = new ImageViewFactory();

    public ImageDialog(String imagePath) throws FileNotFoundException {
        ImageView imageView = imageViewFactory.create(imagePath);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(800);

        getDialogPane().getButtonTypes().add(ButtonType.OK);
        getDialogPane().setId("image-dialog");
        getDialogPane().setContent(imageView);
    }
}
