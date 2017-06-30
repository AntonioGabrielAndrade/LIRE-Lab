package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

class DisplayImageInDialogHandler implements EventHandler<MouseEvent> {

    private Image image;

    public DisplayImageInDialogHandler(Image image) {
        this.image = image;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            ImageDialog dialog = new ImageDialog(image.getImagePath());
            dialog.initOwner(new DialogProvider().getWindowFrom(event));
            dialog.show();

        } catch (FileNotFoundException e) {
            throw new LireLabException("Error displaying image", e);
        }
    }
}
