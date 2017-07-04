package br.com.antoniogabriel.lirelab.custom.imagedialog;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class ImageDialog extends Dialog<Void> {

    public static final double MAX_IMAGE_HEIGHT = 700;
    private final BorderPane contentRoot;

    private ImageViewFactory imageViewFactory;
    private DialogActions dialogActions;
    private ImageViewConfig imageViewConfig;

    public ImageDialog(String imagePath) {
        this(imagePath, new ImageViewFactory(), new ImageViewConfig(), null, new BorderPane());
    }

    public ImageDialog(String imagePath,
                       ImageViewFactory imageViewFactory,
                       ImageViewConfig imageViewConfig,
                       DialogActions dialogActions,
                       BorderPane contentRoot) {

        this.contentRoot = contentRoot;

        setDialogActions(dialogActions);
        setImageViewFactory(imageViewFactory);
        setImageViewConfig(imageViewConfig);
        setImageAsContent(imagePath);
        setDialogTitle(imagePath);
        setAsResizable();
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
        contentRoot.setCenter(imageView);

        dialogActions.setContent(contentRoot);
    }

    private void setDialogTitle(String title) {
        dialogActions.setTitle(title);
    }

    private void setAsResizable() {
        dialogActions.setResizable(true);
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
