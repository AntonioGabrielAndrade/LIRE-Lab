package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;


public class ImageDialog extends Dialog<Void> {

    public static final double MAX_IMAGE_HEIGHT = 700;

    private ImageViewFactory imageViewFactory;
    private DialogActions dialogActions;
    private ImageViewConfig imageViewConfig;

    public ImageDialog(String imagePath) {
        this(imagePath, new ImageViewFactory(), new ImageViewConfig(), null);
    }

    public ImageDialog(String imagePath,
                       ImageViewFactory imageViewFactory,
                       ImageViewConfig imageViewConfig, DialogActions dialogActions) {

        setDialogActions(dialogActions);
        setImageViewFactory(imageViewFactory);
        setImageViewConfig(imageViewConfig);
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
            imageViewConfig.limitImageHeight(imageView, MAX_IMAGE_HEIGHT);

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

    public void setImageViewConfig(ImageViewConfig imageViewConfig) {
        this.imageViewConfig = imageViewConfig;
    }
}
