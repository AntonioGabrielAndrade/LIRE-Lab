package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.inject.Inject;

public class LireLabBuiderFactory implements BuilderFactory {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;
    private ImageGridBuilder imageGridBuilder;
    private CollectionUtils collectionUtils;

    @Inject
    public LireLabBuiderFactory(ImageViewFactory imageViewFactory,
                                FileUtils fileUtils,
                                ImageGridBuilder imageGridBuilder,
                                CollectionUtils collectionUtils) {

        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;
        this.imageGridBuilder = imageGridBuilder;
        this.collectionUtils = collectionUtils;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        if(type == ImageGrid.class) {
            return new ImageGridBuilder(imageViewFactory, fileUtils);
        }
        if(type == CollectionGrid.class) {
            return new CollectionGridBuilder(imageGridBuilder, collectionUtils);
        }

        return new JavaFXBuilderFactory().getBuilder(type);
    }
}
