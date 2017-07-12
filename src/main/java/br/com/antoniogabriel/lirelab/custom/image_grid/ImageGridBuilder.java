package br.com.antoniogabriel.lirelab.custom.image_grid;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.util.Builder;

import javax.inject.Inject;

public class ImageGridBuilder implements Builder<ImageGrid> {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;

    @Inject
    public ImageGridBuilder(ImageViewFactory imageViewFactory, FileUtils fileUtils) {
        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;
    }

    @Override
    public ImageGrid build() {
        return new ImageGrid(imageViewFactory, fileUtils);
    }
}
