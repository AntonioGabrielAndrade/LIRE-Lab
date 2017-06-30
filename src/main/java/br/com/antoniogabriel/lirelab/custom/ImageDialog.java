package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;


public class ImageDialog extends Dialog<Void> {

    private ImageViewFactory imageViewFactory;
    private DialogActions dialogActions;

    public ImageDialog(String imagePath) {
        this(imagePath, new ImageViewFactory(), null);
    }

    public ImageDialog(String imagePath,
                       ImageViewFactory imageViewFactory,
                       DialogActions dialogActions) {

        setDialogActions(dialogActions);
        setImageViewFactory(imageViewFactory);
        setImageAsContent(imagePath);
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
        try {
            ImageView imageView = this.imageViewFactory.create(imagePath);
            imageView.setFitHeight(800);

            this.dialogActions.setContent(imageView);

        } catch (FileNotFoundException e) {
            throw new LireLabException("Could not display image", e);
        }
    }

    private void addOkButton() {
        this.dialogActions.addButtonType(ButtonType.OK);
    }

    private void setDialogPaneId() {
        this.dialogActions.setDialogPaneId("image-dialog");
    }
}
