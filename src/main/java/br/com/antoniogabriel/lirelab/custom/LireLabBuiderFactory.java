package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGridBuilder;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.inject.Inject;

public class LireLabBuiderFactory implements BuilderFactory {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;

    @Inject
    public LireLabBuiderFactory(ImageViewFactory imageViewFactory, FileUtils fileUtils) {
        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        if(type == ImageGrid.class) {
            return new ImageGridBuilder(imageViewFactory, fileUtils);
        }

        return new JavaFXBuilderFactory().getBuilder(type);
    }
}
