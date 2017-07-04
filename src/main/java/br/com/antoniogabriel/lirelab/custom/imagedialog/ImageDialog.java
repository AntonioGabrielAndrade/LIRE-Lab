package br.com.antoniogabriel.lirelab.custom.imagedialog;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;


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
        ImageView imageView = this.imageViewFactory.create(imagePath, false);
        imageViewConfig.limitImageHeight(imageView, MAX_IMAGE_HEIGHT);

        this.dialogActions.setContent(imageView);
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
