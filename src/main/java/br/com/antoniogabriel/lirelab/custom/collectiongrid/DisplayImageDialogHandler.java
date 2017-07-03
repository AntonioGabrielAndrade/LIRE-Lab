package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.imagedialog.ImageDialog;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.FileNotFoundException;

class DisplayImageDialogHandler implements ImageClickHandler {
    private final DialogProvider dialogProvider;
    private final FileUtils fileUtils;

    public DisplayImageDialogHandler(DialogProvider dialogProvider, FileUtils fileUtils) {
        this.dialogProvider = dialogProvider;
        this.fileUtils = fileUtils;
    }

    @Override
    public void handle(Image image, MouseEvent event) {
        try {

            String pathToShow = getImagePathToShow(image);

            Window window = dialogProvider.getWindowFrom(event);
            ImageDialog dialog = dialogProvider.getImageDialog(pathToShow, window);
            dialog.show();

        } catch (FileNotFoundException e) {
            throw new LireLabException("Error displaying image", e);
        }
    }

    private String getImagePathToShow(Image image) {
        return fileUtils.fileExists(image.getImagePath()) ?
                image.getImagePath() :
                image.getThumbnailPath();
    }
}
